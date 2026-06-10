import { StyleSheet, Text, View } from 'react-native';

import { AppButton } from '../components';
import { colors, spacing, typography } from '../constants';

export function PdfGenerationScreen({ navigation }) {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Geracao de PDF</Text>
      <AppButton label="Voltar ao inicio" onPress={() => navigation.popToTop()} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    gap: spacing.lg,
    justifyContent: 'center',
    padding: spacing.lg,
  },
  title: {
    color: colors.textPrimary,
    fontSize: typography.sizes.xl,
    fontWeight: typography.weights.bold,
    textAlign: 'center',
  },
});
