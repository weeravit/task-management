@file:OptIn(ExperimentalFoundationApi::class)

package co.wareverse.taskmanagement.presentation.task_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import co.wareverse.taskmanagement.core.component.RoundTabs
import co.wareverse.taskmanagement.core.theme.BackgroundColor
import co.wareverse.taskmanagement.core.theme.KanitFontFamily
import co.wareverse.taskmanagement.core.theme.PinkColor
import co.wareverse.taskmanagement.core.theme.PurpleColor
import co.wareverse.taskmanagement.core.theme.TitleColor
import co.wareverse.taskmanagement.data.model.TaskStatus
import co.wareverse.taskmanagement.data.model.TodoListModel
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = { TaskStatus.entries.size }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundColor,
        contentColor = Color.Black,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FilterStatus(
                pagerState = pagerState,
            ) {
                coroutineScope.launch {
                    TaskStatus.entries.indexOf(it)
                        .let { pagerState.animateScrollToPage(it) }
                }
            }
        }
    ) {
        HorizontalPager(
            modifier = Modifier.padding(it),
            state = pagerState,
            userScrollEnabled = false,
        ) {
            when (TaskStatus.entries[pagerState.currentPage]) {
                TaskStatus.TODO -> TaskList(
                    modifier = Modifier.testTag(TaskListScreenTestTags.TASK_LIST),
                    pagingData = viewModel.todoPaging,
                    onTaskDeleted = viewModel::deleteTask
                )

                TaskStatus.DOING -> TaskList(
                    modifier = Modifier.testTag(TaskListScreenTestTags.TASK_LIST),
                    pagingData = viewModel.doingPaging,
                    onTaskDeleted = viewModel::deleteTask
                )

                TaskStatus.DONE -> TaskList(
                    modifier = Modifier.testTag(TaskListScreenTestTags.TASK_LIST),
                    pagingData = viewModel.donePaging,
                    onTaskDeleted = viewModel::deleteTask
                )
            }
        }
    }
}

@Composable
private fun TaskList(
    modifier: Modifier = Modifier,
    pagingData: Flow<PagingData<TodoListModel>>,
    onTaskDeleted: (TodoListModel.TaskModel) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val taskList = pagingData.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 40.dp),
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
                                .testTag(TaskListScreenTestTags.TASK_DATE_ITEM)
                                .padding(top = 20.dp)
                                .fillMaxWidth()
                                .animateItemPlacement(tween(1000)),
                            item = model
                        )
                    }

                    is TodoListModel.TaskModel -> {
                        TaskDetailItem(
                            modifier = Modifier
                                .testTag(TaskListScreenTestTags.TASK_DETAIL_ITEM)
                                .padding(top = 8.dp)
                                .background(
                                    color = BackgroundColor,
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .fillMaxWidth()
                                .animateItemPlacement(tween(1000)),
                            item = model,
                            onDelete = onTaskDeleted,
                        )
                    }
                }
            }
        }

        taskList.loadState
            .takeIf { it.refresh is LoadState.Loading || it.append is LoadState.Loading }
            ?.let { item { Loading(modifier = Modifier.fillMaxWidth()) } }

        item { Spacer(modifier = Modifier.height(100.dp)) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskDetailItem(
    modifier: Modifier = Modifier,
    item: TodoListModel.TaskModel,
    onDelete: (TodoListModel.TaskModel) -> Unit,
) {
    var isShow by remember { mutableStateOf(true) }
    val dismissState = rememberDismissState()

    if (dismissState.isDismissed(direction = DismissDirection.EndToStart)) {
        isShow = false
    }

    LaunchedEffect(isShow) {
        if (!isShow) {
            delay(800)
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = isShow,
        exit = fadeOut(spring())
    ) {
        SwipeToDismiss(
            state = dismissState,
            directions = setOf(
                DismissDirection.EndToStart
            ),
            background = {
                val backgroundColor by animateColorAsState(
                    targetValue = when (dismissState.targetValue) {
                        DismissValue.DismissedToStart -> Color.Red.copy(alpha = 0.8f)
                        else -> Color.White
                    },
                    label = "",
                )

                val iconScale by animateFloatAsState(
                    targetValue = when (dismissState.targetValue) {
                        DismissValue.DismissedToStart -> 1.3f
                        else -> 0.5f
                    },
                    label = ""
                )

                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .padding(end = 16.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        modifier = Modifier.scale(iconScale),
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            },
            dismissContent = {
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
        )
    }
}

@Composable
private fun FilterStatus(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onStatusChanged: (TaskStatus) -> Unit,
) {
    val statusList by remember { mutableStateOf(TaskStatus.entries.map { it.display }) }
    val selected by remember(pagerState.currentPage) {
        derivedStateOf { pagerState.currentPage }
    }

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
        onStatusChanged(TaskStatus.entries[it])
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }
}

object TaskListScreenTestTags {
    const val TASK_LIST = "TASK_LIST"
    const val TASK_DATE_ITEM = "TASK_DATE_ITEM"
    const val TASK_DETAIL_ITEM = "TASK_DETAIL_ITEM"
}