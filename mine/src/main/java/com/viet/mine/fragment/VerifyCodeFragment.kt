package com.viet.mine.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chenenyu.router.annotation.Route
import com.safframework.ext.clickWithTrigger
import com.viet.mine.R
import com.viet.mine.viewmodel.AccountInfoViewModel
import com.viet.news.core.config.Config
import com.viet.news.core.delegate.viewModelDelegate
import com.viet.news.core.domain.Settings
import com.viet.news.core.ui.BaseFragment
import com.viet.news.core.ui.code.CodeView
import kotlinx.android.synthetic.main.fragment_mine_verify_code.*

/**
 * @Description 验证码
 * @author null
 * @date 2018/9/10
 * @Email zongjia.long@merculet.io
 * @Version
 */
@Route(value = [Config.ROUTER_MINE_EDIT_VERIFY_CODE_FRAGMENT])
class VerifyCodeFragment : BaseFragment() {
    private var mContainerView: View? = null
    private val model by viewModelDelegate(AccountInfoViewModel::class)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContainerView = inflater.inflate(R.layout.fragment_mine_verify_code, container, false)
        model.startResetPwdCountdown(60000)
        return mContainerView
    }

    @SuppressLint("SetTextI18n")
    override fun initView(view: View) {
        keyboard.hide()
        password_view.clickWithTrigger { keyboard.show() }
        keyboard.setCodeView(password_view)
        keyboard.setListener(object : CodeView.Listener{
            override fun onValueChanged(value: String?) {
                //TODO:待处理
            }

            override fun onComplete(value: String) {

            }

        })
        val phone = Settings.create(context!!).telephone
        phone_num.text = "验证码已经发送至${phone.replaceRange(3..6, "****")}"
        initData()
    }

    private fun initData() {
        model.countDown.observe(this, Observer {
            if (it != null && it > 0 && it < (Config.COUNT_DOWN_TIMER / 1000).toInt()) {
                //正在倒计时
                count_down.text = String.format(getString(R.string.sending_code_s), it.toString())
            } else {
                //倒计时未开始/已结束
                count_down.text = getString(R.string.get_vcode)//重新发送验证码
            }
        })
    }

//    openPage(this@AccountInfoFragment, Config.ROUTER_MINE_EDIT_CHANGE_PWD_FRAGMENT, R.id.container_framelayout)
}