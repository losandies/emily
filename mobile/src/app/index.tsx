import axios from 'axios';
import { useState } from 'react';
import { Button, StyleSheet, Text, TextInput, View } from 'react-native';

type ChatResponse = {
    message: string | null;
    error: string | null;
};

export default function Index() {
    const [message, setMessage] = useState<string>('');
    const [response, setResponse] = useState<ChatResponse | null>(null);
    const [error, setError] = useState(null);

    const sendMessage = async (message: string) => {
        await axios
            .post('http://10.0.0.51:8080/hello', { message })
            .then((res) => setResponse(res.data))
            .catch((err) => setError(err));
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

            {response && (
                <View>
                    <Text>{response.message}</Text>
                </View>
            )}
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
