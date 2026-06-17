package com.onelittleangel.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.onelittleangel.designsystem.theme.PaletteTokens
import com.onelittleangel.designsystem.theme.PaletteTokens.LocalSystemInDarkTheme
import com.onelittleangel.model.DarkThemeConfig
import com.onelittleangel.model.ThemeBrand
import dev.icerock.moko.resources.compose.stringResource
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel(vmClass = SettingsViewModel::class)
) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    val isSystemInDarkTheme = isSystemInDarkTheme()

    SettingsDialog(
        onDismiss = onDismiss,
        settingsUiState = settingsUiState,
        onChangeThemeBrand = viewModel::updateThemeBrand,
        onChangeDarkThemeConfig = {
            when(it) {
                DarkThemeConfig.systemDefault -> LocalSystemInDarkTheme.provides(isSystemInDarkTheme)
                DarkThemeConfig.light -> LocalSystemInDarkTheme.provides(false)
                DarkThemeConfig.dark -> LocalSystemInDarkTheme.provides(true)
            }
            viewModel.updateDarkThemeConfig(it)
        },
    )
}

@Composable
fun SettingsDialog(
    settingsUiState: SettingsUiState,
    onDismiss: () -> Unit,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = { onDismiss() },
        containerColor = if(LocalSystemInDarkTheme.current)
            MaterialTheme.colorScheme.onPrimary
        else
            PaletteTokens.White,
        title = {
            Text(
                text = stringResource(Resources.strings.settings_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                when (settingsUiState) {
                    SettingsUiState.Loading -> {
                        /*Text(
                            text = stringResource(string.loading),
                            modifier = Modifier.padding(vertical = 16.dp)
                        )*/
                    }
                    is SettingsUiState.Success -> {
                        SettingsPanel(
                            settings = settingsUiState.settings,
                            onChangeThemeBrand = onChangeThemeBrand,
                            onChangeDarkThemeConfig = onChangeDarkThemeConfig
                        )
                    }
                }
            }
        },
        confirmButton = {
            Text(
                text = stringResource(Resources.strings.settings_dismiss_dialog_button_text),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp).clickable { onDismiss() }
            )
        }
    )
}

@Composable
private fun SettingsPanel(
    settings: UserEditableSettings,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit
) {
    SettingsDialogSectionTitle(text = stringResource(Resources.strings.settings_theme))

    Column(Modifier.selectableGroup()) {
        SettingsDialogThemeChooserRow(
            text = stringResource(Resources.strings.settings_primary),
            selected = settings.brand == ThemeBrand.primary,
            onClick = { onChangeThemeBrand(ThemeBrand.primary) }
        )

        SettingsDialogThemeChooserRow(
            text = stringResource(Resources.strings.settings_secondary),
            selected = settings.brand == ThemeBrand.secondary,
            onClick = { onChangeThemeBrand(ThemeBrand.secondary) }
        )

        SettingsDialogThemeChooserRow(
            text = stringResource(Resources.strings.settings_tertiary),
            selected = settings.brand == ThemeBrand.tertiary,
            onClick = { onChangeThemeBrand(ThemeBrand.tertiary) }
        )

        SettingsDialogThemeChooserRow(
            text = stringResource(Resources.strings.settings_quaternary),
            selected = settings.brand == ThemeBrand.quaternary,
            onClick = { onChangeThemeBrand(ThemeBrand.quaternary) }
        )
    }
    SettingsDialogSectionTitle(text = stringResource(Resources.strings.settings_dark_mode_preference))
    Column(Modifier.selectableGroup()) {

        SettingsDialogThemeChooserRow(
            text = stringResource(Resources.strings.settings_system_default),
            selected = settings.darkThemeConfig == DarkThemeConfig.systemDefault,
            onClick = {
                onChangeDarkThemeConfig(DarkThemeConfig.systemDefault)
            }
        )

        SettingsDialogThemeChooserRow(
            text = stringResource(Resources.strings.settings_light),
            selected = settings.darkThemeConfig == DarkThemeConfig.light,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.light) }
        )

        SettingsDialogThemeChooserRow(
            text = stringResource(Resources.strings.settings_dark),
            selected = settings.darkThemeConfig == DarkThemeConfig.dark,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.dark) }
        )
    }
}

@Composable
private fun SettingsDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsDialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Spacer(Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}
