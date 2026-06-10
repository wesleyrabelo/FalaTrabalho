import { StyleSheet, Text, View } from "react-native";

import { AppButton } from "../components";
import { colors, spacing, typography } from "../constants";

export function HomeScreen({ navigation }) {
  return (
    <View style={styles.container}>
      <View style={styles.content}>
        <Text style={styles.title}>FalaTrabalho</Text>
        <Text style={styles.description}>
          Vamos ajudar voce a criar um curriculo simples usando respostas
          guiadas.
        </Text>
        <Text style={styles.description}>
          Responda algumas perguntas sobre seus dados, experiencias e
          habilidades. No final, o app gera seu curriculo em PDF.
        </Text>
      </View>

      <AppButton
        label="Gerar meu curriculo"
        onPress={() => navigation.navigate("Interview")}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "space-between",
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
