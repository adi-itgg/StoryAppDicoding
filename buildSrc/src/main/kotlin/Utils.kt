import org.gradle.api.artifacts.dsl.DependencyHandler
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

enum class Type {
    API,
    COMPILE_ONLY,
    IMPLEMENTATION,
    DEBUG,
    TEST,
    ANDROID_TEST,
    KSP
}

fun DependencyHandler.implements(clazz: Any, type: Type = Type.IMPLEMENTATION) {
    clazz::class.declaredMemberProperties.forEach field@{
        if (!it.isConst) return@field
        it.isAccessible = true
        val value = it.getter.call()
        if (value !is String) return@field
        when (type) {
            Type.API -> add("api", value)
            Type.COMPILE_ONLY -> add("compileOnly", value)
            Type.IMPLEMENTATION -> add("implementation", value)
            Type.DEBUG -> add("debugImplementation", value)
            Type.TEST -> add("testImplementation", value)
            Type.ANDROID_TEST -> add("androidTestImplementation", value)
            Type.KSP -> add("ksp", value)
        }
    }
}