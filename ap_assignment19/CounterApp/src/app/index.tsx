import { useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet, SafeAreaView } from 'react-native';

export default function App() {
  const [count, setCount] = useState(0);
  const [isDarkMode, setIsDarkMode] = useState(false);

  const handleIncrement = () => setCount(count + 1);
  const handleDecrement = () => { if (count > 0) setCount(count - 1); };
  const handleReset = () => setCount(0);
  const toggleTheme = () => setIsDarkMode(prev => !prev);

  const bgColor = isDarkMode ? '#1a1a1a' : '#ffffff';
  const textColor = isDarkMode ? '#ffffff' : '#111111';
  const subTextColor = isDarkMode ? '#aaaaaa' : '#888888';

  return (
    <View style={[styles.wrapper, { backgroundColor: bgColor }]}>
      <SafeAreaView style={{ flex: 1 }}>
        <View style={styles.container}>
          <Text style={[styles.title, { color: subTextColor }]}>COUNTER APP</Text>
          <Text style={[styles.counterText, { color: textColor }]}>{count}</Text>
          <View style={styles.row}>
            <TouchableOpacity style={styles.btnDecrement} onPress={handleDecrement}>
              <Text style={styles.btnText}>- Dec</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.btnIncrement} onPress={handleIncrement}>
              <Text style={styles.btnText}>+ Inc</Text>
            </TouchableOpacity>
          </View>
          <TouchableOpacity style={styles.btnReset} onPress={handleReset}>
            <Text style={styles.btnText}>Reset</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.btnTheme} onPress={toggleTheme}>
            <Text style={styles.btnText}>
              {isDarkMode ? 'Switch to Light' : 'Switch to Dark'}
            </Text>
          </TouchableOpacity>
        </View>
      </SafeAreaView>
    </View>
  );
}

const styles = StyleSheet.create({
  wrapper: { flex: 1 },
  container: { flex: 1, alignItems: 'center', justifyContent: 'center', gap: 20, paddingHorizontal: 24 },
  title: { fontSize: 14, fontWeight: '600', letterSpacing: 3, marginBottom: 8 },
  counterText: { fontSize: 96, fontWeight: '700', marginBottom: 16 },
  row: { flexDirection: 'row', gap: 16 },
  btnIncrement: { backgroundColor: '#4CAF50', paddingVertical: 14, paddingHorizontal: 28, borderRadius: 12 },
  btnDecrement: { backgroundColor: '#f44336', paddingVertical: 14, paddingHorizontal: 28, borderRadius: 12 },
  btnReset: { backgroundColor: '#2196F3', paddingVertical: 14, paddingHorizontal: 48, borderRadius: 12 },
  btnTheme: { backgroundColor: '#9C27B0', paddingVertical: 14, paddingHorizontal: 36, borderRadius: 12, marginTop: 8 },
  btnText: { color: '#ffffff', fontSize: 16, fontWeight: '600' },
});
