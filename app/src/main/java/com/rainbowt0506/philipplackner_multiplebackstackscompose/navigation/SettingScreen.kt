package com.rainbowt0506.philipplackner_multiplebackstackscompose.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rainbowt0506.philipplackner_multiplebackstackscompose.CommonBackBar
import com.rainbowt0506.philipplackner_multiplebackstackscompose.R
import com.rainbowt0506.philipplackner_multiplebackstackscompose.model.SettingItem


@Composable
fun SettingScreen(
    onBackClick: () -> Unit,
    onNavToProfile: () -> Unit,
    onNavToSubs: () -> Unit,
    onNavToDiscount: () -> Unit,
    onNavToFaq: () -> Unit,
    onNavToContact: () -> Unit,
    onNavToTerms: () -> Unit,
    onNavToWebsite: () -> Unit,
    onNavToFacebook: () -> Unit,
    onRestorePurchase: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
 ) {
    val context = LocalContext.current
    val showLogoutDialog = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }
    val isSubs = false
    val items = listOf(
        SettingItem(
            resId = R.drawable.ic_setting_personal_setting,
            title = "Profile Setting",
            trailingContent = null,
            onClick = { onNavToProfile() }
        ),
        SettingItem(
            resId = R.drawable.ic_setting_scorelive_pro,
            title = "ScoreLive Pro",
            trailingContent = {
                if (isSubs) {
                    Text(
                        text = "Expire: 2025.05", // or "Go Pro"
                        color = Color(0xFFED6A5E),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                } else {
                    GoProButton(
                        text = "Go Pro",
                        modifier = Modifier
                            .height(34.dp),
                        onClick = { onNavToSubs() }
                    )
                }
            },
            onClick = { /* Handle pro logic */ }
        ),
        SettingItem(
            resId = R.drawable.ic_setting_promo_code,
            title = "Promo Code",
            trailingContent = null,
            onClick = { onNavToDiscount() }
        ),
        null,
        SettingItem(
            resId = R.drawable.ic_setting_faq,
            title = "FAQ",
            trailingContent = null,
            onClick = { onNavToFaq() }
        ),
        SettingItem(
            resId = R.drawable.ic_setting_rate_star,
            title = "Rate Us",
            trailingContent = null,
            onClick = {  }
        ),
        SettingItem(
            resId = R.drawable.ic_setting_contact_us,
            title = "Contact Us",
            trailingContent = null,
            onClick = { onNavToContact() }
        ),
        SettingItem(
            resId = R.drawable.ic_setting_terms,
            title = "Terms & Privacy Policy",
            trailingContent = null,
            onClick = { onNavToTerms() }
        ),
        SettingItem(
            resId = R.drawable.ic_setting_website,
            title = "ScoreLive Website",
            trailingContent = null,
            onClick = { onNavToWebsite() }
        ),
        SettingItem(
            resId = R.drawable.ic_setting_fb,
            title = "ScoreLive on FB",
            trailingContent = null,
            onClick = { onNavToFacebook() }
        ),
        null,
        SettingItem(
            resId = R.drawable.ic_setting_restore_purchase,
            title = "Restore Purchase",
            trailingContent = null,
            onClick = { onRestorePurchase() }
        ),
        SettingItem(
            resId = R.drawable.ic_setting_log_out,
            title = "Log Out",
            trailingContent = null,
            onClick = { onLogout() }
        ),
        SettingItem(
            resId = R.drawable.ic_setting_delete_account,
            title = "Delete Account",
            trailingContent = null,
            onClick = { onDeleteAccount() }
        )
    )

    Scaffold(
        topBar = { CommonBackBar(titleText = "Settings", onBackClick = { onBackClick }) },
    ) { innerPadding ->

        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(32.dp)) }

            itemsIndexed(items) { index, item ->
                Column {
                    SettingRow(item)
                    if (index != items.lastIndex) {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }

            item {
                SettingsFooter(
                    versionText = "10.6",
                    onUpdateClick = {
                    }
                )
            }
        }
    }

    if (showLogoutDialog.value) {
        ConfirmDialog(
            message = "Are you sure you want to log out?",
            onConfirm = {
                showLogoutDialog.value = false
                onLogout()
            },
            onDismiss = { showLogoutDialog.value = false }
        )
    }

    if (showDeleteDialog.value) {
        ConfirmDialog(
            message = "Are you sure you want to delete your account?",
            onConfirm = {
                showDeleteDialog.value = false
                onDeleteAccount()
            },
            onDismiss = { showDeleteDialog.value = false }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SettingScreen(
        onBackClick = {},
        onNavToProfile = {},
        onNavToSubs = {},
        onNavToDiscount = {},
        onNavToFaq = {},
        onNavToContact = {},
        onNavToTerms = {},
        onNavToWebsite = {},
        onNavToFacebook = {},
        onRestorePurchase = {},
        onLogout = {},
        onDeleteAccount = {},
    )
}

@Composable
fun SettingRow(item: SettingItem?) {
    if (item != null) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = item.resId),
                    contentDescription = item.title,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = item.title,
                    fontSize = 18.sp,
                    color = Black,
                    fontWeight = FontWeight.Medium
                )
            }


            if (item.trailingContent == null) {
                Image(
                    painter = painterResource(R.drawable.ic_setting_next),
                    contentDescription = "${item.title} Next",
                    modifier = Modifier.clickable {
                        item.onClick()
                    }
                )
            } else {
                item.trailingContent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingRowPreview() {
    SettingRow(
        item = SettingItem(
            resId = R.drawable.ic_setting_personal_setting,
            title = "Profile Setting",
            trailingContent = null,
            onClick = { /* Handle profile setting */ }
        )
    )
}

@Composable
fun SettingsFooter(
    versionText: String,
    onUpdateClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = versionText,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Text(
            text = "Update Now",
            color = Color.Red,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onUpdateClick() }
        )
    }
}

@Composable
fun GoProButton(
    text: String = "Go Pro",
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Yellow),
        shape = CircleShape,
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GoProButtonPreview() {
    GoProButton(onClick = { /* do something */ })
}

@Composable
fun ConfirmDialog(
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = { Text(text = message) }
    )
}