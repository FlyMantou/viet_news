package com.viet.news.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.viet.news.R
import com.viet.news.core.ui.BaseFragment
import com.viet.news.db.SourceEntity
import com.viet.news.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_find.*

/**
 * @Description 任务
 * @Author sean
 * @Email xiao.lu@magicwindow.cn
 * @Date 03/09/2018 11:13 AM
 * @Version 1.0.0
 */
class FindFragment : BaseFragment(), (SourceEntity) -> Unit {

    private lateinit var model: MineViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_find, container, false)
        model = ViewModelProviders.of(this).get(MineViewModel::class.java)
        return view
    }

    override fun initView(view: View) {
        mBtnChannel.setOnClickListener{
            startActivity(Intent(activity, ChannelActivity::class.java))
        }
    }

    //点击事件
    override fun invoke(source: SourceEntity) {
    }

}