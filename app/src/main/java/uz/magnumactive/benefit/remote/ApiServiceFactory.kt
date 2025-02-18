package uz.magnumactive.benefit.remote

import uz.magnumactive.benefit.App
import uz.magnumactive.benefit.util.Constants
import com.google.gson.*
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


/**
 * Provide "make" methods to create instances of [ApiService]
 * and its related dependencies, such as OkHttpClient, Gson, etc.
 */
object ApiServiceFactory {

    fun makeApiService(isDebug: Boolean): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor(isDebug))
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(makeGson()))
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun makeAuthorizedApiService(isDebug: Boolean): AuthApiService {
        val okHttpClient = OkHttpClient.Builder()
            .cache(Cache(App.INSTANCE!!.cacheDir, 10 * 1024 * 1024L))
            .addInterceptor(MainInterceptor())
            .addInterceptor(makeLoggingInterceptor(isDebug))
            .addInterceptor(AuthInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(makeGson()))
            .build()
        return retrofit.create(AuthApiService::class.java)
    }


    private fun makeGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): Interceptor {
        val logging = HttpLoggingInterceptor(CustomHttpLogging())
        logging.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }

    class CustomHttpLogging : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            val logName = "OkHttp"
            if (!message.startsWith("{")) {
                Timber.tag(logName).d(message)
                return
            }
            try {
                val prettyPrintJson =
                    GsonBuilder().setPrettyPrinting().create().toJson(JsonParser().parse(message))
                Timber.tag(logName).d(prettyPrintJson)
            } catch (m: JsonSyntaxException) {
                Timber.tag(logName).d(message)
            }
        }
    }

}