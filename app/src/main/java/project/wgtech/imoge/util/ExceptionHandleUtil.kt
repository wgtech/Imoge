package project.wgtech.imoge.util

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import project.wgtech.imoge.R
import java.lang.Exception
import java.net.UnknownHostException

class ExceptionHandleUtil(private val exception: Exception, private val context: Context) {

    private var text: String
    private var title: String
    private var iconId: Int = 0

    init {
        if (exception is UnknownHostException) {
            text = context.getString(R.string.error_404_text)
            title = context.getString(R.string.error_404)
            iconId = R.drawable.ic_round_error
        } else {
            text = context.getString(R.string.error_common)
            title = context.getString(R.string.error_common)
            iconId = R.drawable.ic_round_warning
        }
    }

    fun showToast() = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    fun showDialog(positiveActionAsParam: () -> Unit, negativeActionAsParam: () -> Unit) {
        AlertDialog.Builder(context, R.style.AlertDialogTheme)
            .setCancelable(false)
            .setIcon(iconId)
            .setTitle(title)
            .setMessage(text)
            .setPositiveButton(R.string.okay) { dialog, _ -> dialog.dismiss(); positiveActionAsParam() }
            .setNegativeButton(R.string.no) { dialog, _ -> dialog.dismiss(); negativeActionAsParam() }
            .show()
    }
}