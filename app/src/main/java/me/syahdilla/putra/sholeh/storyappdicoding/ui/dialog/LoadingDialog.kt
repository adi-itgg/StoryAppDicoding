package me.syahdilla.putra.sholeh.storyappdicoding.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import me.syahdilla.putra.sholeh.storyappdicoding.databinding.DialogLoadingBinding

class LoadingDialog: DialogFragment(), DefaultLifecycleObserver {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DialogLoadingBinding.inflate(layoutInflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isCancelable = false
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super<DialogFragment>.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun close() = runCatching {
        dismiss()
    }

    fun show(mActivity: AppCompatActivity) {
        mActivity.lifecycle.addObserver(this)
        show(mActivity.supportFragmentManager, this::class.java.simpleName)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        close()
    }

}