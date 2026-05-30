package `in`.skillyards.calltracker.prefs

import android.content.Context
import android.content.SharedPreferences
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever

class CallPreferencesTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockPrefs: SharedPreferences

    @Mock
    private lateinit var mockEditor: SharedPreferences.Editor

    private lateinit var callPreferences: CallPreferences

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        whenever(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockPrefs)
        whenever(mockPrefs.edit()).thenReturn(mockEditor)
        whenever(mockEditor.putString(anyString(), anyOrNull())).thenReturn(mockEditor)
        whenever(mockEditor.putLong(anyString(), anyLong())).thenReturn(mockEditor)
        whenever(mockEditor.putInt(anyString(), anyInt())).thenReturn(mockEditor)
        whenever(mockEditor.putBoolean(anyString(), any())).thenReturn(mockEditor)
        
        callPreferences = CallPreferences(mockContext)
    }

    @Test
    fun testTelecallerIdPersistence() {
        val testId = "12345-abcde"
        callPreferences.telecallerId = testId
        verify(mockEditor).putString("telecaller_id", testId)
        
        whenever(mockPrefs.getString("telecaller_id", null)).thenReturn(testId)
        assertEquals(testId, callPreferences.telecallerId)
    }

    @Test
    fun testDefaultApiBaseUrl() {
        whenever(mockPrefs.getString("api_base_url", "https://blowiest-tyrannicidal-cordie.ngrok-free.dev"))
            .thenReturn("https://blowiest-tyrannicidal-cordie.ngrok-free.dev")
        
        assertEquals("https://blowiest-tyrannicidal-cordie.ngrok-free.dev", callPreferences.apiBaseUrl)
    }

    @Test
    fun testServiceActiveStatus() {
        callPreferences.isServiceActive = true
        verify(mockEditor).putBoolean("is_service_active", true)
        
        whenever(mockPrefs.getBoolean("is_service_active", false)).thenReturn(true)
        assertEquals(true, callPreferences.isServiceActive)
    }
}
