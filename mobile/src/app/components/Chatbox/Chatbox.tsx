import { ChatMessage } from '@/types';
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
                <View key={message.id} className='gap-3 p-4'>
                    <Text
                        className={`max-w-[85%] rounded-2xl px-4 py-3 ${message.role == 'user' ? 'self-end bg-orange-300 text-white text-xl' : 'self-start text-xl text-gray-900'}`}
                    >
                        {message.message}
                    </Text>
                </View>
            ))}
        </ScrollView>
    );
}
