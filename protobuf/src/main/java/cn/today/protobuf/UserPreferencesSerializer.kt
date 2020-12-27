package cn.today.protobuf

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.today.protobuf.UserPreferencesProtos
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreferencesProtos.UserPreferences> {

    override fun readFrom(input: InputStream): UserPreferencesProtos.UserPreferences {
        try {
            return UserPreferencesProtos.UserPreferences.parseFrom(input)
        } catch (exception: Exception) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: UserPreferencesProtos.UserPreferences, output: OutputStream) = t.writeTo(output)

    override val defaultValue: UserPreferencesProtos.UserPreferences
        get() = TODO("Not yet implemented")
}