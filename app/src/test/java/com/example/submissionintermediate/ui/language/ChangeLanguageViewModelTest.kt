package com.example.submissionintermediate.ui.language

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submissionintermediate.data.preferences.UserPreference
import com.example.submissionintermediate.utils.CoroutineTestRule
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ChangeLanguageViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var userPreference: UserPreference

    private lateinit var viewModel: ChangeLanguageViewModel

    @Before
    fun setUp() {
        viewModel = ChangeLanguageViewModel(userPreference)
    }

    @Test
    fun `change language is success`() = runTest {
        userPreference.saveLanguage("id")

        verify(userPreference).saveLanguage("id")
    }
}