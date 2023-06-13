package net.vertexgraphics.myportfolioapp.ui

import android.app.Activity
import android.icu.util.Calendar
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import net.vertexgraphics.myportfolioapp.R
import net.vertexgraphics.myportfolioapp.data.LocalAccountsDataProvider
import net.vertexgraphics.myportfolioapp.model.Email
import net.vertexgraphics.myportfolioapp.model.EmailClientUiState

@Composable
fun EmailClientListOnlyContent(
    emailClientUiState: EmailClientUiState,
    onEmailCardPressed: (Email) -> Unit,
    modifier: Modifier = Modifier
) {
    val emails = emailClientUiState.currentMailboxEmails

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_small)
        )
    ) {

        item {
            EmailClientHomeTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.padding_small))
            )
        }
        items(emails, key = { email -> email.id }) { email ->
            EmailClientEmailListItem(
                email = email,
                selected = false,
                onCardClick = {
                    onEmailCardPressed(email)
                }
            )
        }
    }
}

@Composable
fun EmailClientListAndDetailContent(
    emailClientUiState: EmailClientUiState,
    onEmailCardPressed: (Email) -> Unit,
    modifier: Modifier = Modifier
) {
    val emails = emailClientUiState.currentMailboxEmails
    Row(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(
                    end = dimensionResource(R.dimen.padding_small),
                    top = dimensionResource(R.dimen.padding_small)
                ),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.padding_small)
            )
        ) {
            items(emails, key = { email -> email.id }) { email ->
                EmailClientEmailListItem(
                    email = email,
                    selected = emailClientUiState.currentSelectedEmail.id == email.id,
                    onCardClick = {
                        onEmailCardPressed(email)
                    },
                )
            }
        }
        val activity = LocalContext.current as Activity
        EmailClientDetailsScreen(
            emailClientUiState = emailClientUiState,
            modifier = Modifier.weight(1f),
            onBackPressed = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailClientEmailListItem(
    email: Email,
    selected: Boolean,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            EmailClientEmailItemHeader(
                email,
                Modifier.fillMaxWidth()
            )
            Text(
                text = email.subject,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.padding_small),
                    bottom = dimensionResource(R.dimen.padding_small)
                ),
            )
            Text(
                text = email.body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
private fun EmailClientEmailItemHeader(email: Email, modifier: Modifier = Modifier) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = email.createdAt
    Row(modifier = modifier) {
        EmailClientProfileImage(
            drawableResource = email.sender.avatar,
            description = email.sender.fullName,
            modifier = Modifier.size(dimensionResource(R.dimen.padding_small))
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_small),
                    vertical = dimensionResource(R.dimen.padding_small)
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = email.sender.firstName,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = calendar.time.toString(),
                style = MaterialTheme.typography.labelMedium,

            )
        }
    }
}

@Composable
fun EmailClientProfileImage(
    @DrawableRes drawableResource: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier.clip(CircleShape),
            painter = painterResource(drawableResource),
            contentDescription = description,
        )
    }
}

@Composable
fun Logo(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = stringResource(R.string.logo),
        colorFilter = ColorFilter.tint(color),
        modifier = modifier
    )
}

@Composable
private fun EmailClientHomeTopBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Logo(
            modifier = Modifier
                .size(dimensionResource(R.dimen.logo_size))
                .padding(start = dimensionResource(R.dimen.padding_small))
        )
        EmailClientProfileImage(
            drawableResource = LocalAccountsDataProvider.defaultAccount.avatar,
            description = stringResource(R.string.profile),
            modifier = Modifier
                .padding(end = dimensionResource(R.dimen.padding_small))
                .size(dimensionResource(R.dimen.profile_image_size))
        )
    }
}