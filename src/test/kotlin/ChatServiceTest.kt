
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ChatServiceTest {
    private val chatService = ChatService()

    @Test
    fun createMessage_newMessage() {
        val authorId = UserId(0L)
        val text = "Nest text"
        val message = Message(MessageId(0L), text, authorId)
        val chatId = 0L
        val expected = listOf(message)

        chatService.createMessage(authorId, text)
        val result = chatService.getMessages(chatId)

        assertEquals(expected, result)
    }

    @Test
    fun getUnreadCountEmptyChats_zero() {
        val authorId = UserId(0L)
        val chatId = 0L
        val expected = 0

        val result = chatService.getUnreadCount(chatId, authorId)

        assertEquals(expected, result)
    }

    @Test
    fun getUnreadCountEmptyChats_unreadCount() {
        val authorId = UserId(0L)
        val secondAuthorId = UserId(1L)
        val text = "Test text"
        val chatId = 0L
        val readMessageId = MessageId(10L)
        val expected = 4

        chatService.createMessage(authorId, text)
        repeat(10) {
            chatService.createMessage(authorId, text, chatId)
            chatService.createMessage(secondAuthorId, text, chatId)
        }
        chatService.readMessage(chatId, authorId, readMessageId)
        val result = chatService.getUnreadCount(chatId, authorId)

        assertEquals(expected, result)
    }
}