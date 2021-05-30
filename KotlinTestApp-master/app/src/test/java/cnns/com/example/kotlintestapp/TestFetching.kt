package cnns.com.example.kotlintestapp


import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * On met un test en exemple qu'on pourra réutiliser pour du CI/CD (à étoffer si on veut une couverture bien plus large
 */

class TestFetching {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun fetchById_first() = runBlocking {
        //ici on va simplement vérifier qu'on arrive à bien fetch des éléments sur l'API
        var list = ArrayList<ExampleItem>()
        var checkIsSuperior = false
        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
            try {
                    val response = ApiClient.apiService.getPostById(1)
                    if (response.isSuccessful && response.body() != null) {
                        val content = response.body()
                        val item = ExampleItem(0, "Item X", content?.title, 0)
                        list.add(item)

                        //do something
                    }

                } catch (e: Exception) {
                    // e
            }
            print(list.size)
            //on s'assure d'avoir fetché au moins un élément
            if(list.size > 0 ){
                checkIsSuperior = true
            }
            assertEquals(true, checkIsSuperior)

        }
        val FIN_TEST_HOOKER = 1 // pas utilisé
    }
}
