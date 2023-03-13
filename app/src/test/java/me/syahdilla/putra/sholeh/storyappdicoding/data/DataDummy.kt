package me.syahdilla.putra.sholeh.storyappdicoding.data

import me.syahdilla.putra.sholeh.storyappdicoding.core.domain.model.Story


object DataDummy {

    fun generateDummyStoryResponse(): List<Story> {
        val items: MutableList<Story> = mutableListOf()
        for (i in 0..100) {
            val quote = Story(
                i.toString(),
                "aaa $i",
                description = "desc $i",
                name = "name $i",
                photoUrl = "poto $i"
            )
            items.add(quote)
        }
        return items
    }
}