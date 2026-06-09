import { SafeAreaView, StatusBar, StyleSheet, Text, View } from 'react-native';

import { AppButton } from './components/AppButton';
import { colors, spacing, typography } from './constants';

export default function App() {
  return (
    <SafeAreaView style={styles.safeArea}>
      <StatusBar barStyle="dark-content" backgroundColor={colors.background} />
      <View style={styles.container}>
        <View style={styles.content}>
          <Text style={styles.title}>FalaTrabalho</Text>
          <Text style={styles.description}>
            Estrutura inicial do aplicativo. O fluxo principal sera implementado nas proximas etapas.
          </Text>
        </View>

        <AppButton label="Comecar depois" disabled />
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: colors.background,
  },
  container: {
    flex: 1,
    justifyContent: 'space-between',
    padding: spacing.lg,
  },
  content: {
    gap: spacing.md,
    paddingTop: spacing.xl,
  },
  title: {
    color: colors.textPrimary,
    fontSize: typography.sizes.xl,
    fontWeight: typography.weights.bold,
  },
  description: {
    color: colors.textSecondary,
    fontSize: typography.sizes.md,
    lineHeight: typography.lineHeights.md,
  },
});

