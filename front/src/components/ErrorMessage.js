import { StyleSheet, Text, View } from 'react-native';

import { colors, spacing, typography } from '../constants';

export function ErrorMessage({ message }) {
  if (!message) {
    return null;
  }

  return (
    <View accessibilityRole="alert" style={styles.container}>
      <Text style={styles.text}>{message}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: colors.errorBackground,
    borderColor: colors.error,
    borderRadius: 8,
    borderWidth: 1,
    padding: spacing.md,
  },
  text: {
    color: colors.error,
    fontSize: typography.sizes.sm,
    lineHeight: typography.lineHeights.sm,
  },
});

