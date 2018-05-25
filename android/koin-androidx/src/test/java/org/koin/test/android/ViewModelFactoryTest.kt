package org.koin.test.android

import androidx.lifecycle.ViewModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.android.architecture.KoinFactory
import org.koin.android.architecture.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.get
import org.koin.test.AutoCloseKoinTest
import org.koin.test.ext.junit.assertContexts
import org.koin.test.ext.junit.assertDefinitions
import org.koin.test.ext.junit.assertRemainingInstances


class ViewModelFactoryTest : AutoCloseKoinTest() {

    val module = module {
        single { MyService() }
        viewModel { MyViewModel(get()) }
    }

    class MyService
    class MyViewModel(val service: MyService) : ViewModel()

    @Test
    fun should_create_view_model() {
        startKoin(listOf(module))

        val vm1 = KoinFactory.create(MyViewModel::class.java)
        val vm2 = KoinFactory.create(MyViewModel::class.java)
        val service = get<MyService>()

        assertEquals(vm1.service, vm2.service)
        assertEquals(service, vm2.service)

        assertContexts(1)
        assertDefinitions(2)
        assertRemainingInstances(1)
    }
}