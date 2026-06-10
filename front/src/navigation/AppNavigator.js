import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import { HomeScreen, InterviewScreen, PdfGenerationScreen } from '../screens';
import { colors } from '../constants';

const Stack = createNativeStackNavigator();

export function AppNavigator() {
  return (
    <NavigationContainer>
      <Stack.Navigator
        initialRouteName="Home"
        screenOptions={{
          contentStyle: { backgroundColor: colors.background },
          headerStyle: { backgroundColor: colors.background },
          headerTintColor: colors.textPrimary,
        }}
      >
        <Stack.Screen
          name="Home"
          component={HomeScreen}
          options={{ title: 'FalaTrabalho' }}
        />
        <Stack.Screen
          name="Interview"
          component={InterviewScreen}
          options={{ title: 'Entrevista' }}
        />
        <Stack.Screen
          name="PdfGeneration"
          component={PdfGenerationScreen}
          options={{ title: 'Gerar curriculo' }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
