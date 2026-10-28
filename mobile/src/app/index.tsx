import { ChatResponse } from '@/types';
import axios from 'axios';
import { useState } from 'react';
import { Pressable, Text, TextInput, View } from 'react-native';
import '../global.css';

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
        <View className='flex-1'>
            <View className='flex-1'>
                {response && (
                    <View>
                        <Text>{response.message}</Text>
                    </View>
                )}
            </View>

            <View className='flex-row items-center gap-2 p-4'>
                <TextInput
                    placeholder='Enter a message'
                    className='w-80 rounded-full border border-black p-3 text-black'
                    value={message}
                    onChangeText={setMessage}
                />

                <Pressable onPress={() => sendMessage(message)}>
                    <FaCircleArrowUp />
                </Pressable>
            </View>
        </View>
    );
}
