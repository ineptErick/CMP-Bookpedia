package com.plcoding.bookpedia.book.presentation.book_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.book_cover
import cmp_bookpedia.composeapp.generated.resources.book_error_2
import cmp_bookpedia.composeapp.generated.resources.go_back
import cmp_bookpedia.composeapp.generated.resources.mark_as_favorite
import cmp_bookpedia.composeapp.generated.resources.remove_from_favorites
import coil3.compose.rememberAsyncImagePainter
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.PulseAnimation
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// фоновое изображение с эффектом размытия и несколькими интерактивными элементами
@Composable
fun BlurredImageBackground(
    imageUrl: String?, // URL-адрес изображения (может быть null)
    isFavorite: Boolean, // является ли изображение "избранным"
    onFavoriteClick: () -> Unit, // будет вызвана при нажатии на кнопку "Избранное"
    onBackClick: () -> Unit, // для обработки нажатия кнопки "Назад"
    modifier: Modifier = Modifier, // для настройки внешнего вида компонента
    content: @Composable () -> Unit // контент, который будет отображен внутри
) {
    var imageLoadResult by remember { // состояние для хранения результата загрузки изображения
        mutableStateOf<Result<Painter>?>(null) // для хранения результата загрузки изображения (успех или ошибка)
    }
    val painter = rememberAsyncImagePainter( // асинхронный загрузчик изображения, который будет загружать изображение по указанному URL
        model = imageUrl, // URL изображения для загрузки
        onSuccess = { // при успешной загрузке изображения
            val size = it.painter.intrinsicSize // размеры загруженного изображения (intrinsicSize)
            imageLoadResult = if(size.width > 1 && size.height > 1) { // Проверяет, что размеры больше 1
                Result.success(it.painter) // Если да, сохраняет результат как успешный
            } else { // если нет — создает исключение
                Result.failure(Exception("Invalid image dimensions"))
            }
        },
        onError = { // при ошибке загрузки - Печатает стек вызовов ошибки
            it.result.throwable.printStackTrace()
        }
    )

    Box(modifier = modifier) {
        Column( // вертикальный контейнер
            modifier = Modifier
                .fillMaxSize() // занимает всю доступную область
        ) {
            Box(
                modifier = Modifier
                    .weight(0.3f) // занимает 30% высоты родительского контейнера
                    .fillMaxWidth()
                    .background(DarkBlue)
            ) {
                Image(
                    painter = painter, // Использует загруженное изображение
                    contentDescription = stringResource(Res.string.book_cover),
                    contentScale = ContentScale.Crop, // Масштабирует изображение с обрезкой
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(20.dp) //  эффект размытия
                )
            }
            Box(
                modifier = Modifier // Занимает оставшиеся 70% высоты родительского контейнера с белым фоном
                    .weight(0.7f)
                    .fillMaxWidth()
                    .background(DesertWhite)
            )
        }
        IconButton( //  кнопку с иконкой, которая реагирует на нажатие
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart) // Выравнивание кнопки в верхнем левом углу
                .padding(top = 16.dp, start = 16.dp) // отступы сверху и слева
                .statusBarsPadding() // отступ для учета панели статуса
        ) {
            Icon( // иконку стрелки назад
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, // встроенную иконку стрелки назад
                contentDescription = stringResource(Res.string.go_back),
                tint = Color.White
            )
        }
        Column( // вертикальный контейнер с выравниванием по центру
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.15f)) //  пустое пространство, занимающее 15% высоты родительского контейнера
            ElevatedCard( // карточки с эффектом поднятия (тени)
                modifier = Modifier
                    .height(230.dp) // высоту карточки
                    .aspectRatio(2 / 3f), // соотношение сторон (ширина к высоте) для карточки. В данном случае, ширина будет в 1.5 раза больше высоты (2/3)
                shape = RoundedCornerShape(8.dp), // форму карточки с закругленными углами радиусом 8 dp
                elevation = CardDefaults.elevatedCardElevation( // уровень тени для карточки
                    defaultElevation = 15.dp
                )
            ) {
                AnimatedContent( // анимированный контент, который будет изменяться в зависимости от состояния imageLoadResult
                    targetState = imageLoadResult
                ) { result ->
                    when(result) {
                        null -> Box( // Если результат равен null, отображается контейнер Box
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            PulseAnimation( // анимацию пульсации с размером 60 dp
                                modifier = Modifier
                                    .size(60.dp)
                            )
                        } else -> { // Если результат не равен null
                            Box {
                                Image( // изображение, загруженное из сети или ресурс
                                    painter = if(result.isSuccess) painter //  Если загрузка успешна, используется загруженное изображение
                                    else { painterResource(Res.drawable.book_error_2) // Если загрузка не удалась, используется изображение ошибки
                                    },
                                    contentDescription = stringResource(Res.string.book_cover),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Transparent),
                                    contentScale = if(result.isSuccess) { // Если загрузка успешна, изображение будет обрезано
                                        ContentScale.Crop
                                    } else { // В противном случае, изображение будет подгоняться по размеру
                                        ContentScale.Fit
                                    }
                                )
                                IconButton( // кнопку с иконкой
                                    onClick = onFavoriteClick,
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .background(
                                            brush = Brush.radialGradient( // радиальный градиентный фон для кнопки
                                                colors = listOf(
                                                    SandYellow, Color.Transparent
                                                ),
                                                radius = 70f
                                            )
                                        )
                                ) {
                                    Icon( // иконку в кнопке
                                        imageVector = if(isFavorite) { // Если элемент в избранном, используется заполненная иконка "Избранное"
                                            Icons.Filled.Favorite
                                        } else { // В противном случае, используется контурная иконка "Избранное"
                                            Icons.Outlined.FavoriteBorder
                                        },
                                        tint = Color.Red, // цвет иконки в красный
                                        contentDescription = if(isFavorite) {
                                            stringResource(Res.string.remove_from_favorites)
                                        } else {
                                            stringResource(Res.string.mark_as_favorite)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            content() // Вызывает переданный контент, который будет отображен внутри карточки
        }
    }
}