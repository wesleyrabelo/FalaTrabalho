import { StatusBar } from 'react-native';

import { colors } from './constants';
import { AppNavigator } from './navigation/AppNavigator';

export default function App() {
  return (
    <>
      <StatusBar barStyle="dark-content" backgroundColor={colors.background} />
      <AppNavigator />
    </>
  );
}
