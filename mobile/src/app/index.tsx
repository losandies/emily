import { ChatMessage } from '@/types';
import { FontAwesome6 } from '@react-native-vector-icons/fontawesome6';
import axios from 'axios';
import { useRef, useState } from 'react';
import { Pressable, TextInput, View } from 'react-native';
import { KeyboardAvoidingView } from 'react-native-keyboard-controller';
import Chatbox from '../components/Chatbox/Chatbox';
import '../global.css';

export default function Index() {
    const testMessages: ChatMessage[] = [
        {
            id: 1,
            role: 'user',
            message: 'What have I been working on recently?',
            error: null,
        },
        {
            id: 2,
            role: 'assistant',
            message:
                'You’ve been building your personal assistant app, connecting the mobile frontend to Spring Boot, and learning React Native.',
            error: null,
        },
        {
            id: 3,
            role: 'user',
            message: 'What should I focus on next?',
            error: null,
        },
        {
            id: 4,
            role: 'assistant',
            message:
                'Finish the basic chat interface. Start by displaying messages from both sides, then connect the conversation to your API.',
            error: null,
        },
        {
            id: 5,
            role: 'user',
            message: 'Can you help me understand Java better?',
            error: null,
        },
        {
            id: 6,
            role: 'assistant',
            message:
                'Sure. Which part would you like to practice: classes and objects, collections, exception handling, or how Spring handles HTTP requests?',
            error: null,
        },
    ];

    const nextMessageId = useRef(7);

    const [message, setMessage] = useState<string>('');
    const [isSending, setIsSending] = useState<boolean>(false);
    const [response, setResponse] = useState<ChatMessage | null>(null);
    const [error, setError] = useState(null);
    const [conversation, setConversation] =
        useState<ChatMessage[]>(testMessages);

    const sendMessage = async (message: string) => {
        if (message.trim().length === 0) {
            return;
        }
        setIsSending(true);

        const id = nextMessageId.current;
        nextMessageId.current += 1;

        const userMessage: ChatMessage = {
            role: 'user',
            message: message,
            id: id,
            error: null,
        };

        setConversation((previous) => [...previous, userMessage]);

        setMessage('');

        console.log('Sending:', message);

        await axios
            .post('http://10.0.0.51:8080/hello', { message })
            .then((res) => {
                const id = nextMessageId.current;
                nextMessageId.current += 1;
                console.log(res.data.message);

                const assistantMessage: ChatMessage = {
                    role: 'assistant',
                    message: res.data.message,
                    id: id,
                    error: null,
                };

                setConversation((prev) => [...prev, assistantMessage]);
            })
            .catch(() => {
                setConversation((prev) =>
                    prev.map((msg) => {
                        if (msg.id === userMessage.id) {
                            return {
                                ...msg,
                                error: 'Failed to send',
                            };
                        }
                        return msg;
                    }),
                );
            })
            .finally(() => setIsSending(false));
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
                    className='w-80 min-h-14 max-h-32 rounded-[50] border px-4 py-3 text-black text-lg'
                    value={message}
                    onChangeText={setMessage}
                />

                <Pressable
                    onPress={() => sendMessage(message)}
                    disabled={isSending}
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
