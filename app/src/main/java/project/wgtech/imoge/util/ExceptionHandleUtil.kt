package project.wgtech.imoge.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import project.wgtech.imoge.R

class ExceptionHandleUtil(
    private val context: Context,
    private val drawable: Drawable?,
    private val title: String,
    private val description: String,
) {

    fun showToastShort() = Toast.makeText(context, description, Toast.LENGTH_SHORT).show()
    fun showToastLong() = Toast.makeText(context, description, Toast.LENGTH_LONG).show()
    fun showDialog(positiveActionAsParam: () -> Unit, negativeActionAsParam: () -> Unit) {
        AlertDialog.Builder(context, R.style.Theme_Imoge_AlertDialog)
            .setCancelable(false)
            .setIcon(drawable)
            .setTitle(title)
            .setMessage(description)
            .setPositiveButton(R.string.okay) { dialog, _ -> dialog.dismiss(); positiveActionAsParam() }
            .setNegativeButton(R.string.no) { dialog, _ -> dialog.dismiss(); negativeActionAsParam() }
            .show()
    }
}