package com.plcoding.bookpedia.book.data.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement

// сериализатор для класса BookWorkDto
// реализует интерфейс KSerializer, который является частью библиотеки Kotlin Serialization
class BookWorkDtoSerializer: KSerializer<BookWorkDto> {

    // описывает структуру сериализуемого объекта
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        //  простое имя класса BookWorkDto
        //  !! = значение не является null.
        BookWorkDto::class.simpleName!!
    ){ // элемент с именем "description" типа String?, который будет частью сериализуемого объекта
        element<String?>("description")
    }

    // принимает декодер и возвращает объект BookWorkDto
    // Использует декодер для декодирования структуры, описанной в descriptor
    override fun deserialize(decoder: Decoder): BookWorkDto = decoder.decodeStructure(descriptor) {
        // будет хранить значение свойства description из объекта BookWorkDto
        var description: String? = null
        while(true){ // будет продолжаться до тех пор, пока не встретится условие выхода
            // обработки индексов элементов, полученных из декодера.
            // decodeElementIndex(descriptor) возвращает индекс следующего элемента, который нужно декодировать
            when(val index = decodeElementIndex(descriptor)){
                0 -> { // Если индекс равен 0, это означает, что мы обрабатываем элемент "description"
                    // Приводит декодер к типу JsonDecoder
                    val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException(
                        "This decoder only works with JSON." // Если это невозможно, выбрасывает исключение.
                    )
                    val element = jsonDecoder.decodeJsonElement() // Декодирует элемент JSON
                    description = if(element is JsonObject){ // Проверяет тип декодированного элемента
                        // Если это JsonObject, то используется дополнительная десериализация в объект DescriptionDto
                        decoder.json.decodeFromJsonElement<DescriptionDto>(
                            element = element,
                            deserializer = DescriptionDto.serializer()
                        ).value
                    } else if (element is JsonPrimitive && element.isString){ // Если это JsonPrimitive, и он является строкой,
                        // то просто извлекается его содержимое
                        element.content
                    } else null // В противном случае присваивается значение null
                }
                // Если индекс равен DECODE_DONE
                CompositeDecoder.DECODE_DONE -> break // все элементы были успешно декодированы, и мы выходим из цикла
                // Если индекс не соответствует ожидаемым значениям
                else -> throw SerializationException("Unexpected index $index") // выбрасывается исключение
            }
        } //  Возвращает новый объект BookWorkDto, созданный с использованием значения description
        return@decodeStructure BookWorkDto(description)
    }
// принимает кодировщик и объект BookWorkDto, который нужно сериализовать
    override fun serialize(encoder: Encoder, value: BookWorkDto) = encoder.encodeStructure(
        // Начинает процесс сериализации структуры, используя ранее определенный дескриптор.
        descriptor
    ) {
        value.description?.let{ //  Если значение description не равно null
            // одирует строковый элемент с индексом 0 (т.е. "description") и значением из it,
            // которое является значением свойства description
            encodeStringElement(descriptor, 0, it)
        }
    }
}