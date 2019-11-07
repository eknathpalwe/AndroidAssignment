package com.androidassignment.model.local

import com.androidassignment.data.OperationCallback
import com.androidassignment.model.Facts
import com.androidassignment.model.FactsDataSource
import com.androidassignment.model.FactsResponse

class FactsLocalRepository : FactsDataSource {
    override fun getFactsList(operationCallback: OperationCallback<FactsResponse>?) {

        val mockFactsResponse = FactsResponse(
            "About Canada",
            listOf(
                Facts(
                    "Beavers",
                    "Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony",
                    "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
                ), Facts(
                    "Flag",
                    null,
                    "http://images.findicons.com/files/icons/662/world_flag/128/flag_of_canada.png"
                ), Facts(
                    "Transportation",
                    "It is a well known fact that polar bears are the main mode of transportation in Canada. They consume far less gas and have the added benefit of being difficult to steal.",
                    "http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg"
                ), Facts(
                    "Hockey Night in Canada",
                    "These Saturday night CBC broadcasts originally aired on radio in 1931. In 1952 they debuted on television and continue to unite (and divide) the nation each week.",
                    "http://fyimusic.ca/wp-content/uploads/2008/06/hockey-night-in-canada.thumbnail.jpg"
                ), Facts(
                    "Eh",
                    "A chiefly Canadian interrogative utterance, usually expressing surprise or doubt or seeking confirmation.",
                    null
                ), Facts(
                    "Housing",
                    "description 7",
                    "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
                ), Facts(
                    null,
                    null,
                    null
                ), Facts(
                    "Space Program",
                    "description 7",
                    "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
                ), Facts(
                    "Meese",
                    "description 7",
                    "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
                )
            )
        )
        operationCallback?.onSuccess(mockFactsResponse)
    }
}