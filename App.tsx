import React from 'react';
import {
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
  NativeModules,
  Platform,
} from 'react-native';

// Reference to your native module
const { MyActivityLauncher } = NativeModules;

const App = () => {
  const handlePress = () => {
    if (Platform.OS === 'android') {
     MyActivityLauncher.openActivity();
    } else {
      console.warn('Native chat activity is Android-only');
    }
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.roundButton} onPress={handlePress}>
        <Text style={styles.buttonText}>Let's Chat</Text>
      </TouchableOpacity>
    </View>
  );
};

export default App;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f0f0f0',
  },
  roundButton: {
    backgroundColor: '#A90000',
    borderRadius: 50,
    paddingVertical: 14,
    paddingHorizontal: 40,
    elevation: 3,
  },
  buttonText: {
    color: '#fff',
    fontSize: 18,
    fontWeight: '600',
  },
});
