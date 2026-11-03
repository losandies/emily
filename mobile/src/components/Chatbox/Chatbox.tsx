import { ChatMessage } from '@/types';
import { Ionicons } from '@react-native-vector-icons/ionicons';
import { useRef } from 'react';
import { ScrollView, ScrollViewInstance, Text, View } from 'react-native';

type ChatBoxProps = {
    messages: ChatMessage[];
};
export default function Chatbox({ messages }: ChatBoxProps) {
    const scrollViewRef = useRef<ScrollViewInstance | null>(null);

    return (
        <ScrollView
            ref={scrollViewRef}
            onContentSizeChange={() => {
                scrollViewRef.current?.scrollToEnd({ animated: true });
            }}
            onLayout={() => {
                scrollViewRef.current?.scrollToEnd({ animated: false });
            }}
            contentContainerClassName='flex-1 flex-grow justify-end gap-3 p-4'
        >
            {messages.map((message) => (
                <View
                    key={message.id}
                    className={`flex flex-col ${
                        message.role === 'user'
                            ? 'self-end items-center'
                            : 'self-start'
                    }`}
                >
                    <View className='flex flex-row items-center gap-2 px-4 pt-4'>
                        <Text
                            className={`max-w-[250px] rounded-2xl px-4 py-3 ${
                                message.role === 'user'
                                    ? 'bg-orange-300 text-white text-xl'
                                    : 'text-xl text-gray-900'
                            }`}
                        >
                            {message.message}
                        </Text>

                        {message.error && message.role === 'user' && (
                            <Ionicons
                                name='alert-circle-outline'
                                size={30}
                                color='red'
                            />
                        )}
                    </View>

                    {message.error && message.role === 'user' && (
                        <Text className='text-neutral-600 mt-2'>
                            {message.error}
                        </Text>
                    )}
                </View>
            ))}
        </ScrollView>
    );
}
