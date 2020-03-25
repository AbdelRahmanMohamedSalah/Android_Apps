package com.mazad.mazadangy.utels

import android.content.Context
import android.widget.Toast
import es.dmoral.toasty.Toasty

class ToastUtel {
    companion object {
        public fun showSuccessToast(context: Context, toastMessage: String) {
            showSuccessToastMessage(context, toastMessage, Toast.LENGTH_SHORT)
        }

        public fun classicToastst(context: Context, toastMessage: String) {
            showToastMessage(context, toastMessage, Toast.LENGTH_SHORT)
        }

        public fun waningToast(context: Context, toastMessage: String) {
            showWarningToastMessage(context, toastMessage, Toast.LENGTH_SHORT)
        }

        public fun errorToast(context: Context, toastMessage: String) {
            showErrorToastMessage(context, toastMessage, Toast.LENGTH_SHORT)
        }

        public fun infoToast(context: Context, toastMessage: String) {
            showInfoToastMessage(context, toastMessage, Toast.LENGTH_SHORT)
        }

        private fun showToastMessage(context: Context, toastMessage: String, length: Int) {
            Toast.makeText(context, toastMessage, length).show()
        }

        private fun showSuccessToastMessage(context: Context, toastMessage: String, length: Int) {
            Toasty.success(context, toastMessage, length).show()
        }

        private fun showWarningToastMessage(context: Context, toastMessage: String, length: Int) {
            Toasty.warning(context, toastMessage, length).show()
        }

        private fun showInfoToastMessage(context: Context, toastMessage: String, length: Int) {
            Toasty.info(context, toastMessage, length).show()
        }

        private fun showErrorToastMessage(context: Context, toastMessage: String, length: Int) {
            Toasty.error(context, toastMessage, length).show()
        }


    }
}