package com.serj113.mygithub.ui

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.runner.AndroidJUnit4
import com.serj113.data.di.RepositoryModule
import com.serj113.domain.base.Entity
import com.serj113.domain.entity.User
import com.serj113.domain.repository.UserRepository
import com.serj113.mygithub.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class MainActivityThirdTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(ApplicationComponent::class)
    object RepositoryModule {
        @Provides
        @Singleton
        fun provideUserRepository(): UserRepository {
            return mockUserRepository
        }
    }

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun mainActivityTest() {
        coEvery { mockUserRepository.searchUser(any(), any(), any()) } returns flow {
            emit(Entity.loading<List<User>>())
            val users = listOf(
                User(
                    116087,
                    "octo",
                    "https://avatars0.githubusercontent.com/u/116087?v=4",
                    "https://api.github.com/users/octo"
                ),
                User(
                    583231,
                    "octocat",
                    "https://avatars3.githubusercontent.com/u/583231?v=4",
                    "https://api.github.com/users/octocat"
                )
            )
            emit(Entity.success(users))
        }.flowOn(Dispatchers.Default)
        ActivityScenario.launch(MainActivity::class.java)
        val appCompatEditText = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.query_edit_text),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatEditText.perform(ViewActions.replaceText("octo"), ViewActions.closeSoftKeyboard())

        Thread.sleep(1000);

        val linearLayout = onView(
            Matchers.allOf(
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.recyclerview),
                        childAtPosition(
                            IsInstanceOf.instanceOf(LinearLayout::class.java),
                            1
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        linearLayout.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    companion object {
        val mockUserRepository = mockk<UserRepository>()
    }
}
