package co.wareverse.taskmanagement.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import co.wareverse.taskmanagement.data.api.APIService
import co.wareverse.taskmanagement.data.local.AppDatabase
import co.wareverse.taskmanagement.data.model.RemoteKeyEntity
import co.wareverse.taskmanagement.data.model.TaskEntity
import co.wareverse.taskmanagement.data.model.TaskStatus

@OptIn(ExperimentalPagingApi::class)
class TaskRemoteMediator(
    private val taskStatus: TaskStatus,
    private val apiService: APIService,
    private val appDatabase: AppDatabase,
) : RemoteMediator<Int, TaskEntity>() {
    private val taskDao by lazy { appDatabase.taskDao() }
    private val remoteKeyDao by lazy { appDatabase.remoteKeyDao() }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TaskEntity>
    ): MediatorResult {
       return try {
           val loadKey = when (loadType) {
               LoadType.REFRESH -> 0
               LoadType.PREPEND -> return MediatorResult.Success(
                   endOfPaginationReached = true
               )
               LoadType.APPEND -> {
                   val remoteKey = appDatabase.withTransaction {
                       remoteKeyDao.remoteKeyByQuery(taskStatus.value)
                   }

                   remoteKey.nextKey
               }
           }
           val limit = state.config.pageSize

           val response = apiService.fetchTodoList(
               offset = loadKey,
               limit = limit,
               sortBy = "createdAt",
               isAsc = true,
               status = taskStatus.value,
           )
           val tasks = response.body()?.tasks.orEmpty()
           val nextKey = loadKey.plus(1)

           appDatabase.withTransaction {
               if (loadType == LoadType.REFRESH) {
                   remoteKeyDao.deleteByQuery(taskStatus.value)
                   taskDao.deleteByStatus(taskStatus.value)
               }

               remoteKeyDao.insertOrReplace(
                   RemoteKeyEntity(taskStatus.value, nextKey)
               )
               taskDao.insertAll(tasks)
           }

           MediatorResult.Success(
               endOfPaginationReached = tasks.size < limit
           )
       } catch (e: Exception) {
           MediatorResult.Error(e)
       }
    }
}