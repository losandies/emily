import { ChatMessage } from '@/types';
import { FontAwesome6 } from '@react-native-vector-icons/fontawesome6';
import axios from 'axios';
import { useRef, useState } from 'react';
import { Pressable, TextInput, View } from 'react-native';
import { KeyboardAvoidingView } from 'react-native-keyboard-controller';
import '../global.css';
import Chatbox from './components/Chatbox/Chatbox';

export default function Index() {
    const testMessages: ChatMessage[] = [
        {
            id: 1,
            role: 'user',
            message: 'What have I been working on recently?',
        },
        {
            id: 2,
            role: 'assistant',
            message:
                'You’ve been building your personal assistant app, connecting the mobile frontend to Spring Boot, and learning React Native.',
        },
        {
            id: 3,
            role: 'user',
            message: 'What should I focus on next?',
        },
        {
            id: 4,
            role: 'assistant',
            message:
                'Finish the basic chat interface. Start by displaying messages from both sides, then connect the conversation to your API.',
        },
        {
            id: 5,
            role: 'user',
            message: 'Can you help me understand Java better?',
        },
        {
            id: 6,
            role: 'assistant',
            message:
                'Sure. Which part would you like to practice: classes and objects, collections, exception handling, or how Spring handles HTTP requests?',
        },
    ];

    const nextMessageId = useRef(7);

    const [message, setMessage] = useState<string>('');
    const [response, setResponse] = useState<ChatMessage | null>(null);
    const [error, setError] = useState(null);
    const [conversation, setConversation] =
        useState<ChatMessage[]>(testMessages);

    const sendMessage = async (message: string) => {
        if (message.trim().length === 0) {
            return;
        }
        const id = nextMessageId.current;
        nextMessageId.current += 1;

        const userMessage: ChatMessage = {
            role: 'user',
            message: message,
            id: id,
        };

        setConversation((previous) => [...previous, userMessage]);

        await axios
            .post('http://10.0.0.51:8080/hello', { message })
            .then((res) => {
                const id = nextMessageId.current;
                nextMessageId.current += 1;

                const assistantMessage: ChatMessage = {
                    role: 'assistant',
                    message: res.data.message,
                    id: id,
                };

                setConversation((previous) => [...previous, assistantMessage]);
            })
            .catch((err) => setError(err))
            .finally(() => setMessage(''));
    };

    return (
        <KeyboardAvoidingView
            behavior='padding'
            style={{ flex: 1 }}
            automaticOffset
        >
            <View className='flex-1'>
                <Chatbox messages={conversation} />
            </View>
            <View className='flex-row items-center justify-center gap-2 p-4'>
                <TextInput
                    multiline
                    placeholder='Enter a message'
                    className='w-80 min-h-14 max-h-32 rounded-md border border-black px-4 py-3 text-black text-lg'
                    value={message}
                    onChangeText={setMessage}
                />

                <Pressable
                    onPress={() => sendMessage(message)}
                    className='flex justify-center items-center'
                >
                    <FontAwesome6
                        name='circle-arrow-up'
                        iconStyle='solid'
                        size={45}
                        color={message == '' ? '#d1d5db' : '#fdba74'}
                    />
                </Pressable>
            </View>
        </KeyboardAvoidingView>
    );
}
