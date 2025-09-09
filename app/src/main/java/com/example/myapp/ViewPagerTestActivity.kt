package com.example.myapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.random.Random

class ViewPagerTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 创建主布局
        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        // 添加ViewPager (旧版)部分
        addViewPagerSection(mainLayout, "ViewPager (旧版)")
        
        // 添加ViewPager2 (新版)部分
        addViewPager2Section(mainLayout, "ViewPager2 (新版)")

        setContentView(mainLayout)
    }

    private fun addViewPagerSection(parent: LinearLayout, title: String) {
        // 添加标题
        val titleView = TextView(this).apply {
            text = title
            textSize = 20f
            setPadding(0, 16.dpToPx(), 0, 8.dpToPx())
        }
        parent.addView(titleView)

        // 添加TabLayout
        val tabLayout = TabLayout(this)
        parent.addView(tabLayout)

        // 添加ViewPager
        val viewPager = ViewPager(this).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                200.dpToPx()
            ).apply {
                bottomMargin = 24.dpToPx()
            }
            adapter = object : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                override fun getCount(): Int = 3 // 3个Fragment
                override fun getItem(position: Int) = TestFragment.newInstance(position)
                override fun getPageTitle(position: Int) = "Page ${position + 1}"
            }
        }
        parent.addView(viewPager)

        // 关联TabLayout和ViewPager
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun addViewPager2Section(parent: LinearLayout, title: String) {
        // 添加标题
        val titleView = TextView(this).apply {
            text = title
            textSize = 20f
            setPadding(0, 16.dpToPx(), 0, 8.dpToPx())
        }
        parent.addView(titleView)

        // 添加TabLayout
        val tabLayout = TabLayout(this)
        parent.addView(tabLayout)

        // 添加ViewPager2
        val viewPager2 = ViewPager2(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                200.dpToPx()
            )
            adapter = object : FragmentStateAdapter(this@ViewPagerTestActivity) {
                override fun getItemCount(): Int = 3 // 3个Fragment
                override fun createFragment(position: Int) = TestFragment.newInstance(position)
            }
        }
        parent.addView(viewPager2)

        // 关联TabLayout和ViewPager2
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = "Page ${position + 1}"
        }.attach()
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = 26 // A-Z 共26个字母

    override fun getItem(position: Int): Fragment {
        return TestFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return ('A' + position).toString()
    }
}

class ViewPager2Adapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 26 // A-Z 共26个字母

    override fun createFragment(position: Int): Fragment {
        return TestFragment.newInstance(position)
    }
}

class LetterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textView: TextView = itemView.findViewById(android.R.id.text1)
    
    fun bind(letter: String) {
        textView.text = letter
        textView.setTextColor(Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        ))
        textView.layoutParams.height = 44.dpToPx()
    }
    
    private fun Int.dpToPx(): Int = (this * itemView.resources.displayMetrics.density).toInt()
}

class LetterAdapter : RecyclerView.Adapter<LetterViewHolder>() {
    private val letters = ('A'..'Z').map { it.toString() }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return LetterViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        holder.bind(letters[position])
    }
    
    override fun getItemCount(): Int = letters.size
}

class TestFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return RecyclerView(requireContext()).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = LetterAdapter()
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    companion object {
        fun newInstance(position: Int): TestFragment {
            return TestFragment()
        }
    }
}