import { useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';

import { AppButton } from '../components';
import { colors, spacing, typography } from '../constants';

const questions = [
  {
    id: 'personalInfo.name',
    title: 'Qual e o seu nome completo?',
    help: 'Fale seu nome como quer que apareca no curriculo.',
  },
  {
    id: 'personalInfo.cityState',
    title: 'Em qual cidade e estado voce mora?',
    help: 'Diga o nome da cidade e a sigla ou nome do estado.',
  },
  {
    id: 'personalInfo.maritalStatus',
    title: 'Qual e o seu estado civil?',
    help: 'Por exemplo: solteiro, casado, separado ou viuvo.',
  },
  {
    id: 'personalInfo.age',
    title: 'Qual e a sua idade?',
    help: 'Diga apenas sua idade em anos.',
  },
  {
    id: 'personalInfo.phoneNumber',
    title: 'Qual e o seu telefone?',
    help: 'Diga o numero com DDD.',
  },
  {
    id: 'professionalGoal',
    title: 'Qual tipo de trabalho voce procura?',
    help: 'Se nao tiver um cargo certo, diga a area em que gostaria de trabalhar.',
  },
  {
    id: 'professionalSummary',
    title: 'Fale um pouco sobre voce como profissional.',
    help: 'Conte suas principais experiencias, pontos fortes e o tipo de trabalho que sabe fazer.',
  },
  {
    id: 'education',
    title: 'Qual e a sua escolaridade?',
    help: 'Diga o curso, escola, periodo e se concluiu ou ainda esta estudando.',
  },
  {
    id: 'workExperience',
    title: 'Conte sobre um trabalho que voce ja teve.',
    help: 'Diga a empresa ou local, sua funcao, periodo em que trabalhou e o que fazia.',
  },
  {
    id: 'complementaryCourses',
    title: 'Voce fez algum curso ou qualificacao?',
    help: 'Se fez, diga o nome do curso, onde fez, carga horaria, periodo e o que aprendeu.',
  },
  {
    id: 'qualities',
    title: 'Quais sao suas principais qualidades?',
    help: 'Diga caracteristicas importantes para o trabalho, como responsabilidade, pontualidade ou facilidade para aprender.',
  },
];

export function InterviewScreen({ navigation }) {
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);

  const currentQuestion = questions[currentQuestionIndex];
  const isFirstQuestion = currentQuestionIndex === 0;
  const isLastQuestion = currentQuestionIndex === questions.length - 1;

  function handleNext() {
    if (isLastQuestion) {
      navigation.navigate('PdfGeneration');
      return;
    }

    setCurrentQuestionIndex((index) => index + 1);
  }

  function handleBack() {
    if (isFirstQuestion) {
      navigation.goBack();
      return;
    }

    setCurrentQuestionIndex((index) => index - 1);
  }

  return (
    <View style={styles.container}>
      <View style={styles.content}>
        <Text style={styles.progress}>
          Pergunta {currentQuestionIndex + 1} de {questions.length}
        </Text>
        <Text style={styles.title}>{currentQuestion.title}</Text>
        <Text style={styles.help}>{currentQuestion.help}</Text>
      </View>

      <View style={styles.actions}>
        <AppButton label={isFirstQuestion ? 'Voltar ao inicio' : 'Pergunta anterior'} onPress={handleBack} />
        <AppButton label={isLastQuestion ? 'Gerar curriculo' : 'Proxima pergunta'} onPress={handleNext} />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'space-between',
    padding: spacing.lg,
  },
  content: {
    gap: spacing.md,
    paddingTop: spacing.xl,
  },
  progress: {
    color: colors.primary,
    fontSize: typography.sizes.sm,
    fontWeight: typography.weights.bold,
  },
  title: {
    color: colors.textPrimary,
    fontSize: typography.sizes.xl,
    fontWeight: typography.weights.bold,
  },
  help: {
    color: colors.textSecondary,
    fontSize: typography.sizes.md,
    lineHeight: typography.lineHeights.md,
  },
  actions: {
    gap: spacing.md,
  },
});
