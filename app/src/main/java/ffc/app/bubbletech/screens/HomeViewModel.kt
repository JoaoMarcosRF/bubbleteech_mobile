package ffc.app.bubbletech.screens

import androidx.lifecycle.ViewModel
import ffc.app.bubbletech.data.FakeRepository

class HomeViewModel: ViewModel() {

    private val repository = FakeRepository()

    val posts = repository.getPosts()

}