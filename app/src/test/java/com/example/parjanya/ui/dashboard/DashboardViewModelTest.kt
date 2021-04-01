package com.example.parjanya.ui.dashboard

import android.content.Context
import androidx.lifecycle.Observer
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.parjanya.model.WeatherRequest
import com.example.parjanya.network.RequestService
import com.example.parjanya.network.Result

import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception
import java.net.HttpURLConnection
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DashboardViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    internal lateinit var context: Context

    private lateinit var requestService: RequestService

    private lateinit var viewModel: DashboardViewModel

    private lateinit var dashboardUseCase: DashboardUseCase

    private lateinit var dashboardRepository: DashboardRepository


    private lateinit var mockWebServer: MockWebServer

    @Mock
    private lateinit var textObserver: Observer<String>



    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        requestService = RequestService(context)
        dashboardRepository = DashboardRepository(requestService)
        dashboardUseCase = DashboardUseCase(dashboardRepository)
        viewModel = DashboardViewModel(dashboardUseCase)
        viewModel.text.observeForever(textObserver)



        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Test
    fun `read sample failure json file`(){
        val reader = MockResponseReader("failed.json")
        assertNotNull(reader.content)
    }


    @Test
    fun `check if view model is null` () {
        assertNotNull(viewModel)
    }

    @Test
    fun `check if dashboard use case is null` () {
        assertNotNull(dashboardUseCase)
    }

    @Test
    fun `throw error if latitude is empty in request`() {

        runBlocking {
            dashboardUseCase.fetchDataWeatherUseCase(WeatherRequest("","12.746", "minutely,hourly,alerts"))
            dashboardUseCase.useCaseResponse.observeForever {
                val error  = (it as Result.Error)
                assertNotNull(error)
            }
        }
    }

    @Test
    fun `throw error if longitude is empty in request`() {

        runBlocking {
            dashboardUseCase.fetchDataWeatherUseCase(WeatherRequest("32.74647","", "minutely,hourly,alerts"))
            dashboardUseCase.useCaseResponse.observeForever {
                val error  = (it as Result.Error)
                assertNotNull(error)
            }
        }
    }

    @Test
    fun `throw error if location coordinates is empty in request`() {

        runBlocking {
            dashboardUseCase.fetchDataWeatherUseCase(WeatherRequest("","", "minutely,hourly,alerts"))
            dashboardUseCase.useCaseResponse.observeForever {
                val error  = (it as Result.Error)
                assertNotNull(error)
            }
        }
    }

    @Test
    fun `handle error response if request is wrong values`() {

        val mockResponse = MockResponse()
        val reader = MockResponseReader("failed.json")
        mockResponse.setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockResponse.setBody(reader.content)
        mockWebServer.enqueue(mockResponse)

        runBlocking {
            //val data = requestService.getWeatherData(WeatherRequest("18.52","12.746","minutely,hourly,alerts"))
        }
    }




    @After
    fun tearDown(){
        viewModel.text.removeObserver(textObserver)
        mockWebServer.shutdown()
    }
}