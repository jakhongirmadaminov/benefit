package uz.magnumactive.benefit.remote

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import java.util.concurrent.TimeUnit

class MainInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        request.tag(Invocation::class.java)?.let {
            if (!it.method().isAnnotationPresent(Cacheable::class.java)) {
                builder.cacheControl(
                    CacheControl.Builder()
                        .noStore()
                        .build()
                )
                return chain.proceed(builder.build())
            }
            try {
                builder.cacheControl(CacheControl.FORCE_NETWORK)
                return chain.proceed(builder.build())
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            builder.cacheControl(
                CacheControl.Builder()
                    .maxStale(Int.MAX_VALUE, TimeUnit.DAYS)
                    .build()
            )
        }
        return chain.proceed(builder.build())
    }
}