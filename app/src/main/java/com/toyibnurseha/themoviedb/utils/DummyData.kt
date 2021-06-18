package com.toyibnurseha.themoviedb.utils

import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity

object DummyData {

    fun generateMovieData(): List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()

        movies.add(
            MovieEntity(
                "/8ChCpCYxh9YXusmHwcE9YzP0TSG.jpg",
                337404,
                "Cruella",
                "In 1970s London amidst the punk rock revolution, a young grifter named Estella is determined to make a name for herself with her designs. She befriends a pair of young thieves who appreciate her appetite for mischief, and together they are able to build a life for themselves on the London streets. One day, Estella’s flair for fashion catches the eye of the Baroness von Hellman, a fashion legend who is devastatingly chic and terrifyingly haute. But their relationship sets in motion a course of events and revelations that will cause Estella to embrace her wicked side and become the raucous, fashionable and revenge-bent Cruella.",
                4362.743,
                "/hjS9mH8KvRiGHgjk6VUZH7OT0Ng.jpg",
                "2021-05-26",
                "Cruella",
                voteAverage = 8.6
            )
        )

        movies.add(
            MovieEntity(
                "/wwFBRyekDcKXJwP0mImRJjAnudL.jpg\"",
                632357,
                "The Unholy",
                "Alice, a young hearing-impaired girl who, after a supposed visitation from the Virgin Mary, is inexplicably able to hear, speak and heal the sick. As word spreads and people from near and far flock to witness her miracles, a disgraced journalist hoping to revive his career visits the small New England town to investigate. When terrifying events begin to happen all around, he starts to question if these phenomena are the works of the Virgin Mary or something much more sinister.",
                3816.574,
                "/6wxfWZxQcuv2QgxIQKj0eYTdKTv.jpg",
                "2021-03-31",
                "The Unholy"
            )
        )

        movies.add(
            MovieEntity(
                "/9WlJFhOSCPnaaSmsrv0B4zA8iUb.jpg",
                503736 ,
                "Army of the Dead",
                "Following a zombie outbreak in Las Vegas, a group of mercenaries take the ultimate gamble: venturing into the quarantine zone to pull off the greatest heist ever attempted.",
                3080.336,
                "/z8CExJekGrEThbpMXAmCFvvgoJR.jpg",
                "2021-05-14",
                "Army of the Dead"
            )
        )

        movies.add(
            MovieEntity(
                "/77tui163estZrQ78NBggqDB4n2C.jpg",
                637649,
                "Wrath of Man",
                "A cold and mysterious new security guard for a Los Angeles cash truck company surprises his co-workers when he unleashes precision skills during a heist. The crew is left wondering who he is and where he came from. Soon, the marksman's ultimate motive becomes clear as he takes dramatic and irrevocable steps to settle a score.",
                2468.743,
                "/YxopfHpsCV1oF8CZaL4M3Eodqa.jpg",
                "2021-04-22",
                "Wrath of Man"
            )
        )

        movies.add(
            MovieEntity(
                "/mPBI506o7gITnjoSkcyPneK9s8n.jpg",
                460465,
                "Mortal Kombat",
                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                1775.97,
                "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg",
                "2021-04-07",
                "Mortal Kombat"
            )
        )

        return movies
    }

    fun generateShowsData(): List<TVShowEntity> {
        val show = ArrayList<TVShowEntity>()

        show.add(
            TVShowEntity(
                "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg",
                "2005-03-27",
                1416,
                "Grey's Anatomy",
                overview = "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
                popularity = 1984.268,
                posterPath = "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
                voteAverage = 8.2
            )
        )

        show.add(
            TVShowEntity(
                "/9Jmd1OumCjaXDkpllbSGi2EpJvl.jpg",
                "2014-10-07",
                60735,
                "The Flash",
                overview = "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \\\"meta-human\\\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                popularity = 1005.8,
                posterPath = "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                voteAverage = 7.7
            )
        )

        show.add(
            TVShowEntity(
                "/iDbIEpCM9nhoayUDTwqFL1iVwzb.jpg",
                "2017-09-25",
                71712,
                "The Good Doctor",
                overview = "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives",
                popularity = 1001.957,
                posterPath = "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
                voteAverage = 8.6
            )
        )

        show.add(
            TVShowEntity(
                "/dYvIUzdh6TUv4IFRq8UBkX7bNNu.jpg",
                "2021-03-24",
                120168,
                "Who Killed Sara?",
                overview = "Hell-bent on exacting revenge and proving he was framed for his sister's murder, Álex sets out to unearth much more than the crime's real culprit.",
                popularity = 854.199,
                posterPath = "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg",
                voteAverage = 7.8,
            )
        )

        show.add(
            TVShowEntity(
                "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg",
                "2005-03-27",
                1416,
                "Grey's Anatomy",
                overview = "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
                popularity = 779.658,
                posterPath = "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg",
                voteAverage = 8.2
            )
        )

        return show
    }

}