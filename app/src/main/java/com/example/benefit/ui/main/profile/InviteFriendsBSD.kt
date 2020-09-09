package com.example.benefit.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benefit.R
import com.example.benefit.util.MyBSDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bsd_invite_friend.*


@AndroidEntryPoint
class InviteFriendsBSD : MyBSDialog() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_invite_friend, null)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnShare.setOnClickListener {
            /*Create an ACTION_SEND Intent*/
            val intent = Intent(Intent.ACTION_SEND)
            /*This will be the actual content you wish you share.*/
            val shareBody = edtUrl.text
            /*The type of the content is text, obviously.*/intent.type = "text/plain"
            /*Applying information Subject and Body.*/
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share subject")
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            /*Fire!*/
            startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
        }

    }
}