package com.example.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager_test)

        // ViewPager (旧版)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        // ViewPager2 (新版)
        val viewPager2 = findViewById<ViewPager2>(R.id.viewPager2)
        val tabLayout2 = findViewById<TabLayout>(R.id.tabLayout2)
        viewPager2.adapter = ViewPager2Adapter(this)
        
        TabLayoutMediator(tabLayout2, viewPager2) { tab, position ->
            tab.text = "Page ${position + 1}"
        }.attach()
    }
}

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return TestFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "Page ${position + 1}"
    }
}

class ViewPager2Adapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return TestFragment.newInstance(position + 1)
    }
}

class TestFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pageNumber = arguments?.getInt("page") ?: 1
        view.findViewById<TextView>(R.id.pageNumberTextView).text = "Page $pageNumber"
    }

    companion object {
        fun newInstance(pageNumber: Int): TestFragment {
            val fragment = TestFragment()
            val args = Bundle()
            args.putInt("page", pageNumber)
            fragment.arguments = args
            return fragment
        }
    }
}