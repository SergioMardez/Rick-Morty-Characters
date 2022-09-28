package com.sergiom.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sergiom.data.extensions.getNextPage
import com.sergiom.data.net.RestClient
import com.sergiom.data.net.response.CharacterEntity
import javax.inject.Inject

class CharacterDataSource @Inject constructor(
    private val restClient: RestClient
): PagingSource<Int, CharacterEntity>()  {

    private var currentPage = 1
    var query = ""
    private var previousQuery = ""

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        return try {
            currentPage = params.key ?: 1
            checkQueries()
            val result = restClient.getRemoteCaller().getSearchedCharactersNextPage(currentPage, query)
            val data: MutableList<CharacterEntity> = result.body()?.results?.toMutableList() ?: mutableListOf()
            LoadResult.Page(
                data = data,
                prevKey = result.body()?.info?.prev.getNextPage(),
                nextKey = result.body()?.info?.next.getNextPage()
            )
        } catch (t: Throwable) {
            LoadResult.Error(throwable = t)
        }
    }

    private fun checkQueries() {
        if ((previousQuery.length != query.length) || ((this.previousQuery.length == this.query.length) && (previousQuery != query))) {
            currentPage = 1
            previousQuery = query
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}