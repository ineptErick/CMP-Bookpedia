package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.book_error_2
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.LightBlue
import com.plcoding.bookpedia.core.presentation.PulseAnimation
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import kotlin.math.round

// создает элемент списка для книги
// с возможностью обработки нажатий,
// асинхронной загрузкой изображения и отображением информации о книге
@Composable
fun BookListItem(
    book: Book, // объект книги, содержащий информацию, такую как название и изображение
    onClick: () -> Unit, // функция обратного вызова, которая будет вызвана при нажатии на элемент списка
    modifier: Modifier = Modifier // модификатор для настройки внешнего вида и поведения элемента
) {
    Surface( // контейнер с заданными свойствами (например, закругленные углы и цвет фона)
        // может использоваться для создания карточек, кнопок и других элементов интерфейса.
        shape = RoundedCornerShape(32.dp), // Определяет форму поверхности. RoundedCornerShape создает закругленные углы с радиусом 32dp.
        modifier = modifier
            // обрабатывает нажатия благодаря модификатору clickable
            .clickable(onClick = onClick),
        color = LightBlue.copy(alpha = 0.2f) // цвет фона для Surface. используется цвет LightBlue с измененной прозрачностью (альфа-канал) до 20%.
    ) {
        Row( // Элемент компоновки, который располагает дочерние элементы в строку
            // используется для размещения изображения книги и текста рядом друг с другом
            modifier = Modifier
                .padding(16.dp) // Добавляет отступы вокруг Row в 16dp.
                .fillMaxWidth() // Заставляет Row занимать всю доступную ширину.
                .height(IntrinsicSize.Min), // Устанавливает высоту равной минимально необходимой высоте для содержимого.
            verticalAlignment = Alignment.CenterVertically, //  Устанавливает вертикальное выравнивание дочерних элементов по центру.
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Определяет расстояние между дочерними элементами в строке. Здесь устанавливается расстояние в 16dp.
        ) {
            Box( // Контейнер, который позволяет размещать элементы поверх друг друга
                // используется для отображения изображения книги
                modifier = Modifier
                    .height(100.dp), //  Устанавливает высоту Box в 100dp.
                contentAlignment = Alignment.Center // выравнивание содержимого внутри Box по центру
            ) {
                var imageLoadResult by remember { // Создает состояние для хранения результата загрузки изображения.
                    // Используется remember для сохранения состояния при перерисовке.
                    mutableStateOf<Result<Painter>?>(null) // Изначально значение равно null.
                }
                // вспомогательная функция для асинхронной загрузки изображения по URL.
                // Она принимает URL изображения и два обработчика:
                // один для успешной загрузки и другой для обработки ошибок.
                val painter = rememberAsyncImagePainter(
                    model = book.imageUrl,
                    onSuccess = { // Обработчик, который вызывается при успешной загрузке изображения
                        imageLoadResult = // Состояние, которое хранит результат загрузки изображения (успех или ошибка).
                            // Если размеры загруженного изображения больше единицы, то результат считается успешным.
                            if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                                Result.success(it.painter)
                            } else { // В противном случае возникает ошибка с сообщением "Неверный размер изображения".
                                Result.failure(Exception("Invalid image size"))
                            }
                    },
                    onError = { // Обработчик, который вызывается при ошибке загрузки изображения.
                        // выводит стек вызовов ошибки в консоль и устанавливает результат загрузки как неудачный.
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )
                // Получает текущее состояние загрузки изображения (успешно загружено,
                // загружается или произошла ошибка) с помощью функции collectAsStateWithLifecycle,
                // которая автоматически управляет состоянием в зависимости от жизненного цикла компонента.
                val painterState by painter.state.collectAsStateWithLifecycle()
                // Анимированное значение, которое изменяется в зависимости от состояния загрузки изображения.
                val transition by animateFloatAsState(
                    // Если изображение успешно загружено, значение равно 1f;
                    targetValue = if(painterState is AsyncImagePainter.State.Success) {
                        1f
                    } else { // иначе — 0f.
                        0f
                    }, // Используется анимация с продолжительностью 800 миллисекунд.
                    animationSpec = tween(durationMillis = 800)
                )
                // Отображение изображения
                // Условная конструкция для проверки результата загрузки изображения:
                when (val result = imageLoadResult) {
                    // Если результат равен null, отображается анимация пульсации с размером 60dp.
                    null -> PulseAnimation(
                        modifier = Modifier.size(60.dp)
                    )
                    // Если изображение успешно загружено, происходит проверка на успешность загрузки
                    else -> {
                        Image( // Если загрузка успешна (result.isSuccess), используется загруженное изображение (painter).
                            painter = if (result.isSuccess) painter else {
                                // В противном случае отображается изображение ошибки (book_error_2).
                                painterResource(Res.drawable.book_error_2)
                            },
                            contentDescription = book.title, // Описание содержимого для доступности (например, для экранных читалок). Здесь используется название книги.
                            contentScale = if (result.isSuccess) { //  Определяет, как изображение будет масштабироваться внутри контейнера.
                                ContentScale.Crop // Изображение будет обрезано так, чтобы заполнить контейнер.
                            } else {
                                ContentScale.Fit // Изображение будет масштабироваться так, чтобы полностью поместиться в контейнер без обрезки.
                            },
                            modifier = Modifier
                                .aspectRatio( // Устанавливает соотношение сторон для изображения.
                                    ratio = 0.65f, // ширина будет 0.65 от высоты
                                    matchHeightConstraintsFirst = true // Указывает, что сначала следует применять ограничения по высоте, а затем по ширине.
                                )
                                .graphicsLayer { // Позволяет применять трансформации к компоненту, такие как вращение и масштабирование.
                                    rotationX = (1f - transition) * 30f // Устанавливает вращение по оси X. Используется значение, зависящее от анимационного перехода transition, что создает эффект наклона.
                                    val scale = 0.8f + (0.2f * transition) // Устанавливают масштабирование по осям X и Y.
                                    // Значение рассчитывается на основе transition, что позволяет изображению "пульсировать".
                                    scaleX = scale
                                    scaleY = scale
                                }
                        )
                    }
                }
            }
            // Отображение текста
            Column( // Контейнер, который располагает дочерние элементы вертикально.
                // В данном случае используется для отображения названия книги и имени автора.
                modifier = Modifier
                    .fillMaxHeight() // Заставляет колонку занимать всю доступную высоту.
                    .weight(1f), // Указывает, что колонка должна занимать оставшееся пространство в ряду.
                verticalArrangement = Arrangement.Center // Определяет вертикальное расположение дочерних элементов в колонне. Здесь устанавливается центрирование.
            ) {
                Text( // Компонент для отображения текста.
                    // Здесь он используется для отображения названия книги и имени первого автора.
                    text = book.title, // Текст, который будет отображен — название книги.
                    style = MaterialTheme.typography.titleMedium, // Стиль текста, здесь используется стиль заголовка среднего размера из темы Material.
                    maxLines = 2, // Максимальное количество строк для отображения текста. Установлено в 2 строки.
                    overflow = TextOverflow.Ellipsis // Определяет поведение текста, если он превышает максимальное количество строк.
                // Здесь используется TextOverflow.Ellipsis, что добавляет многоточие в конце текста.
                )
                book.authors.firstOrNull()?.let { authorName -> //  Проверяет, есть ли авторы у книги.
                    // Если есть, то берет первого автора (firstOrNull()) и передает его в лямбда-функцию как authorName.
                    Text(
                        text = authorName, // Имя автора.
                        style = MaterialTheme.typography.bodyLarge, // Стиль текста — большой текст из темы Material.
                        maxLines = 1, // Ограничивает отображение до одной строки.
                        overflow = TextOverflow.Ellipsis // Использует многоточие для обрезанного текста.
                    )
                }
                book.averageRating?.let { rating -> // Проверяет, есть ли средний рейтинг у книги (averageRating).
                    // Если есть, то передает его в лямбда-функцию как rating.
                    Row(
                        // Создает горизонтальный контейнер Row, который выравнивает дочерние элементы по центру вертикально.
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text( // Отображает текст рейтинга:
                            text = "${round(rating * 10) / 10.0}", // Форматирует рейтинг до одного знака после запятой (например, 4.5).
                            style = MaterialTheme.typography.bodyMedium // Стиль текста — средний текст из темы Material.
                        )
                        Icon( // Отображает иконку звезды рядом с рейтингом:
                            imageVector = Icons.Default.Star, // Использует стандартную иконку звезды из библиотеки Icons.
                            contentDescription = null, // Пустое значение, так как это декоративная иконка.
                            tint = SandYellow // Устанавливает цвет иконки — здесь используется цвет SandYellow.
                        )
                    }
                }
            }
            Icon( // Отображает иконку стрелки вправо
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, // Использует стандартную иконку стрелки вправо из библиотеки Icons.
                contentDescription = null, // Пустое значение, так как это декоративная иконка.
                modifier = Modifier //  Устанавливает размер иконки в 36dp.
                    .size(36.dp)
            )
        }
    }
}

@Composable
fun PulseAnimation(modifier: Modifier) {
    TODO("Not yet implemented")
}


