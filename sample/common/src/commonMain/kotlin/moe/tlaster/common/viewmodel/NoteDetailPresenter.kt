package moe.tlaster.common.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.Flow
import moe.tlaster.common.model.Note
import moe.tlaster.common.repository.FakeRepository
import moe.tlaster.common.util.rememberInMemory

class NoteDetailPresenter(
    private val id: Int,
) {
    @Composable
    fun present(intents: Flow<NoteDetailIntent>): NoteDetailState {
        // var node by rememberInMemory {
        //     mutableStateOf<Note?>(null)
        // }

        // just for test
        var state by rememberInMemory {
            mutableStateOf<NoteDetailState>(NoteDetailState.Loading)
        }

        LaunchedEffect(Unit) {
            FakeRepository.getStateFlow(id).collect {
                state = NoteDetailState.Success(it)
            }
        }
        // return if (node != null) {
        //     NoteDetailState.Success(node!!)
        // } else {
        //     NoteDetailState.Loading
        // }
        return state
    }
}

sealed class NoteDetailIntent

sealed class NoteDetailState {
    object Loading : NoteDetailState()
    data class Success(val note: Note) : NoteDetailState()
}
