package com.sample.userstory.di

import com.sample.userstory.ui.dashboard.DashboardFragment
import com.sample.userstory.ui.detail.DetailFragment
import com.sample.userstory.ui.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment
}