package tech.mujtaba.entain.feature.race.data.service

import retrofit2.http.GET
import retrofit2.http.Query
import tech.mujtaba.entain.feature.race.data.NextRacesResponse

internal interface RaceService {

    @GET("racing/?method=nextraces")
    suspend fun getNextRaces(@Query("count") count: Int): NextRacesResponse
}
