package co.wareverse.taskmanagement.presentation.task_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import co.wareverse.taskmanagement.core.component.RoundTabs
import co.wareverse.taskmanagement.core.theme.KanitFontFamily
import co.wareverse.taskmanagement.core.theme.PinkColor
import co.wareverse.taskmanagement.core.theme.PurpleColor
import co.wareverse.taskmanagement.core.theme.TitleColor
import co.wareverse.taskmanagement.data.model.TaskStatus
import co.wareverse.taskmanagement.data.model.TodoListModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel = hiltViewModel()
) {
    val taskList = viewModel.paging.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.load(
            status = TaskStatus.TODO
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        contentColor = Color.Black,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FilterStatus(
                lazyListState = lazyListState
            ) {
                viewModel.load(status = it)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(horizontal = 40.dp),
            state = lazyListState,
        ) {
            items(
                count = taskList.itemCount,
                key = taskList.itemKey { it.id },
            ) { index ->
                taskList[index]?.let { model ->
                    when (model) {
                        is TodoListModel.DateGroupTasksModel -> {
                            TaskDateItem(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth(),
                                item = model
                            )
                        }

                        is TodoListModel.TaskModel -> {
                            TaskDetailItem(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .fillMaxWidth(),
                                item = model
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun TaskDateItem(
    modifier: Modifier = Modifier,
    item: TodoListModel.DateGroupTasksModel,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = item.date,
            fontFamily = KanitFontFamily,
            fontWeight = FontWeight.W800,
            fontSize = 20.sp,
            color = TitleColor,
        )
    }
}

@Composable
private fun TaskDetailItem(
    modifier: Modifier = Modifier,
    item: TodoListModel.TaskModel,
) {
    Column(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AsyncImage(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = item.imageUrl,
                contentDescription = null,
            )

            Column {
                Text(
                    text = item.title,
                    fontFamily = KanitFontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    color = PurpleColor
                )

                Text(
                    text = item.description,
                    fontFamily = KanitFontFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 16.sp,
                    color = Color(0xFF9C9A9B)
                )
            }
        }
    }
}

@Composable
private fun FilterStatus(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    onStatusChanged: (TaskStatus) -> Unit,
) {
    val statusList by remember { mutableStateOf(TaskStatus.values().map { it.display }) }
    var selected by remember { mutableIntStateOf(0) }
    var previousFirstVisibleItemIndex by remember { mutableIntStateOf(0) }
    val shouldShowFab by remember(lazyListState) {
        derivedStateOf {
            val isShow = lazyListState.firstVisibleItemIndex == 0 ||
                    lazyListState.firstVisibleItemIndex < previousFirstVisibleItemIndex

            if (previousFirstVisibleItemIndex != lazyListState.firstVisibleItemIndex) {
                previousFirstVisibleItemIndex = lazyListState.firstVisibleItemIndex
            }

            isShow
        }
    }

    AnimatedVisibility(
        visible = shouldShowFab,
        enter = scaleIn(),
        exit = scaleOut(),
    ) {
        RoundTabs(
            modifier = modifier,
            backgroundColor = Color(0xFFF8F8F8),
            textSelectedColor = Color.White,
            textUnselectedColor = Color(0xFFC5C5C5),
            indicatorColor = Brush.horizontalGradient(
                colors = listOf(PinkColor, PurpleColor),
            ),
            items = statusList,
            selectedItemIndex = selected,
        ) {
            selected = it
            onStatusChanged(TaskStatus.values()[it])
        }
    }
}