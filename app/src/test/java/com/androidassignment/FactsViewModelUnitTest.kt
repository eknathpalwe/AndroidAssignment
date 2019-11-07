package com.androidassignment

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.androidassignment.data.OperationCallback
import com.androidassignment.model.Facts
import com.androidassignment.model.FactsDataSource
import com.androidassignment.model.FactsResponse
import com.androidassignment.viewmodel.FactsViewModel
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FactsViewModelUnitTest {

    @Mock
    private lateinit var repository: FactsDataSource
    @Mock
    private lateinit var context: Application

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback<FactsResponse>>

    private lateinit var viewModel: FactsViewModel

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var onRenderFactsObserver: Observer<List<Facts>>

    private lateinit var factsEmptyList: List<Facts>
    private lateinit var factsList: List<Facts>
    private lateinit var factsResponse: FactsResponse
    private lateinit var factsEmptyListResponse: FactsResponse

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`<Context>(context.applicationContext).thenReturn(context)

        viewModel = FactsViewModel(context, repository, repository)

        mockData()
        setupObservers()
    }

    @Test
    fun factsEmptyListRepositoryAndViewModel() {
        with(viewModel) {
            isViewLoading.observeForever(isViewLoadingObserver)
            isEmptyList.observeForever(emptyListObserver)
            factsList.observeForever(onRenderFactsObserver)
        }

        verify(repository, times(1)).getFactsList(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(factsEmptyListResponse)

        assertNotNull(viewModel.isViewLoading.value)
        assertTrue(viewModel.isEmptyList.value == true)
        assertTrue(viewModel.factsList.value?.size == 0)
    }

    @Test
    fun factsListRepositoryAndViewModel() {
        with(viewModel) {
            isViewLoading.observeForever(isViewLoadingObserver)
            factsList.observeForever(onRenderFactsObserver)
        }

        verify(repository, times(1)).getFactsList(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(factsResponse)

        assertNotNull(viewModel.isViewLoading.value)
        assertTrue(viewModel.factsList.value?.size == 6)
    }

    @Test
    fun factsFailRepositoryAndViewModel() {
        with(viewModel) {
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }
        verify(repository, times(1)).getFactsList(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("Error")
        assertNotNull(viewModel.isViewLoading.value)
        assertNotNull(viewModel.onMessageError.value)
    }

    private fun setupObservers() {
        isViewLoadingObserver = mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = mock(Observer::class.java) as Observer<Any>
        emptyListObserver = mock(Observer::class.java) as Observer<Boolean>
        onRenderFactsObserver = mock(Observer::class.java) as Observer<List<Facts>>
    }

    private fun mockData() {
        factsEmptyList = emptyList()
        factsList = listOf(
            Facts(
                "mock title 1",
                "description 1",
                "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
            ), Facts(
                "mock title 2",
                "description 2",
                "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
            ), Facts(
                "mock title 3",
                "description 3",
                "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
            ), Facts(
                "mock title 4",
                "description 4",
                "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
            ), Facts(
                "mock title 6",
                "description 6",
                "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
            ), Facts(
                "mock title 7",
                "description 7",
                "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
            )
        )
        factsResponse = FactsResponse(
            "facts mock",
            factsList
        )

        factsEmptyListResponse = FactsResponse("empty response", factsEmptyList)
    }
}
