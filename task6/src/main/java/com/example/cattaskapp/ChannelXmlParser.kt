package com.example.cattaskapp

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class ChannelXmlParser {

    private var channels: MutableList<Channel> = mutableListOf()

    fun getChannel(): MutableList<Channel>
    {
        return channels
    }

    fun parse(xmlData: String?): Boolean
    {

        var status = true
        var currentChannel: Channel = Channel()
        var inEntry = false
        var textValue = ""

        var indexSeach: Int = 0

        try
        {

            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(StringReader(xmlData))
            var eventType = xpp.eventType

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                val tagName = xpp.name

                when (eventType)
                {
                    XmlPullParser.START_TAG ->
                    {
                        when
                        {
                            "item".equals(tagName, ignoreCase = true) -> {
                                inEntry = true
                                currentChannel = Channel()
                            }
                            "enclosure".equals(tagName, ignoreCase = true) -> {

                                var resultString: String? = null

                                if (xmlData != null) {

                                    val textsrav: String = "enclosure url="
                                    indexSeach = xmlData.indexOf(textsrav, indexSeach)

                                    indexSeach+=textsrav.length
                                    var nullString: Int = xmlData.indexOf(" ", indexSeach)

                                    resultString = xmlData.substring(indexSeach+1, nullString-1)

                                }



                                currentChannel?.setUrl(resultString)
                            }
                        }
                    }

                    XmlPullParser.TEXT -> textValue = xpp.text

                    XmlPullParser.END_TAG -> if (inEntry)
                    {
                        when
                        {
                            "item".equals(tagName, ignoreCase = true) ->
                            {
                                channels!!.add(currentChannel)
                                inEntry = false
                            }
                            "title".equals(tagName, ignoreCase = true) ->
                            {
                                currentChannel?.setTitle(textValue)
                            }
                            "description".equals(tagName, ignoreCase = true) ->
                            {
                                currentChannel?.setDescription(textValue)
                            }

                        }
                    }
                }
                eventType = xpp.next()
            }
        } catch (e: Exception) { status = false }

        return status
    }

}