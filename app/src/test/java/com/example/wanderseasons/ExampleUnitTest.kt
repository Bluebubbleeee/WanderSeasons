package com.example.wanderseasons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.wanderseasons.model.LoginResponse
import com.example.wanderseasons.repository.MainRepository
import com.example.wanderseasons.network.ApiService
import com.example.wanderseasons.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MainViewModel
    private lateinit var repository: MainRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Use your actual base URL
        val retrofit = Retrofit.Builder()
            .baseUrl("https://nit3213api.onrender.com//")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        repository = MainRepository(apiService)
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with valid credentials should return success`() = runTest {
        val validUsername = "Tasnim"
        val validPassword = "s8073439"
        val campus = "sydney"

        val observer = Observer<LoginResponse> { result ->
            Assert.assertNotNull("Login response should not be null", result)
            Assert.assertTrue("Keypass should not be empty", result.keypass.isNotEmpty())
        }

        viewModel.loginResult.observeForever(observer)
        viewModel.login(campus, validUsername, validPassword)

        advanceUntilIdle()
        viewModel.loginResult.removeObserver(observer)
    }
}
