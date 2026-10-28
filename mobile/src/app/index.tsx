import { useState } from 'react';
import { Button, StyleSheet, TextInput, View } from 'react-native';

export default function Index() {
    const [message, setMessage] = useState<string>('');

    const sendMessage = (message: string) => {
        fetch('http://10.0.0.51:8080/hello', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ message }),
        });
    };

    return (
        <View style={styles.container}>
            <TextInput
                placeholder='Enter a message'
                style={{
                    width: 250,
                    borderWidth: 1.5,
                    padding: 12,
                    borderRadius: 50,
                }}
                value={message}
                onChangeText={setMessage}
            />
            <Button title='Submit' onPress={() => sendMessage(message)} />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
});
